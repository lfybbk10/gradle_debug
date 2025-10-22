package ru.mentee.power.tdd.notes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты для NoteService")
class NoteServiceTest {

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteService();
    }

    @Nested
    @DisplayName("Тесты добавления заметок")
    class AddNoteTests {
        @Test
        @DisplayName("Добавление валидной заметки")
        void shouldAddValidNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "тест");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);

            // Assert
            assertThat(addedNote).isNotNull();
            assertThat(addedNote.getId()).isGreaterThan(0);
            assertThat(addedNote.getTitle()).isEqualTo(title);
            assertThat(addedNote.getText()).isEqualTo(text);
            assertThat(addedNote.getTags()).isEqualTo(tags);
            assertThat(noteService.getAllNotes().size()).isEqualTo(1);
        }

        @Test
        @DisplayName("Добавление заметки с null title/text")
        void shouldHandleNullTitleAndText() {
            assertThatThrownBy(()->noteService.addNote(null, null, null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Добавление заметки с empty тегами")
        void shouldHandleNullEmptyTags() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            Note addedNote2 = noteService.addNote(title, text, null);

            // Assert
            assertThat(addedNote.getTags()).isEqualTo(Set.of("test"));
            assertThat(addedNote2.getTags()).isEqualTo(Set.of());
        }
    }

    @Nested
    @DisplayName("Тесты получения заметок")
    class GetNoteTests {

        @Test
        @DisplayName("Тест для getNodeById")
        void shouldGetNodeById() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);

            //Assert
            assertThat(noteService.getNoteById(addedNote.getId()).get()).isEqualTo(addedNote);
        }

        @Test
        @DisplayName("Тест для несуществующего id getNodeById")
        void shouldGetNodeByNotExistsId() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);

            //Assert
            assertThat(noteService.getNoteById(5)).isEmpty();
        }

        @Test
        @DisplayName("Тест для getAllNodes")
        void shouldGetAllNodes() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            List<Note> expectedNotes = new ArrayList<>();
            expectedNotes.add(noteService.addNote(title, text, tags));
            expectedNotes.add(noteService.addNote("Вторая", "Текст", tags));

            //Assert
            assertThat(noteService.getAllNotes()).containsExactlyElementsOf(expectedNotes);
        }

        @Test
        @DisplayName("Тест для getAllNodes")
        void shouldGetAllNodesEmpty() {
            assertThat(noteService.getAllNotes()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Тесты обновления заметок")
    class UpdateNoteTests {
        @Test
        @DisplayName("Тест для updateNoteText")
        void shouldUpdateNoteText() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean updateStatus = noteService.updateNoteText(addedNote.getId(), "Обновлено название", "Обновлён текст");

            //Assert
            Note gettingFromService = noteService.getNoteById(addedNote.getId()).get();

            assertThat(updateStatus).isTrue();
            assertThat(gettingFromService.getTitle()).isEqualTo("Обновлено название");
            assertThat(gettingFromService.getText()).isEqualTo("Обновлён текст");
        }

        @Test
        @DisplayName("Тест для updateNoteText с несуществующим id")
        void shouldUpdateNoteTextNotExist() {
            boolean updateStatus = noteService.updateNoteText(5, "Обновлено название", "Обновлён текст");

            assertThat(updateStatus).isFalse();
        }

        @Test
        @DisplayName("Тест для addTagToNote")
        void shouldAddTagToNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean addedStatus = noteService.addTagToNote(addedNote.getId(),"code");

            //Assert
            assertThat(addedStatus).isTrue();
            assertThat(noteService.getNoteById(addedNote.getId()).get().getTags()).contains("code");
        }

        @Test
        @DisplayName("Тест для addTagToNote с существующим тегом")
        void shouldAddExistTagToNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean addedStatus = noteService.addTagToNote(addedNote.getId(),"java");

            //Assert
            assertThat(addedStatus).isFalse();
        }

        @Test
        @DisplayName("Тест для addTagToNote с несуществующей заметкой")
        void shouldAddTagToNotExistNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean addedStatus = noteService.addTagToNote(5,"java");

            //Assert
            assertThat(addedStatus).isFalse();
        }

        @Test
        @DisplayName("Тест для removeTagFromNote")
        void shouldRemoveTagFromNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean addedStatus = noteService.removeTagFromNote(addedNote.getId(),"test");

            //Assert
            assertThat(addedStatus).isTrue();
            assertThat(noteService.getNoteById(addedNote.getId()).get().getTags()).doesNotContain("test");
        }

        @Test
        @DisplayName("Тест для removeTagFromNote с несуществующим тегом")
        void shouldRemoveNotExistTagFromNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean addedStatus = noteService.addTagToNote(addedNote.getId(),"java");

            //Assert
            assertThat(addedStatus).isFalse();
        }

        @Test
        @DisplayName("Тест для removeTagFromNote с несуществующей заметкой")
        void shouldRemoveTagFromNotExistNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean addedStatus = noteService.removeTagFromNote(5,"java");

            //Assert
            assertThat(addedStatus).isFalse();
        }

    }

    @Nested
    @DisplayName("Тесты удаления заметок")
    class DeleteNoteTests {
        @Test
        @DisplayName("Тест для deleteNode")
        void shouldDeleteNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean deleteStatus = noteService.deleteNote(addedNote.getId());

            //Assert
            assertThat(deleteStatus).isTrue();
            assertThat(noteService.getNoteById(addedNote.getId())).isEmpty();
        }

        @Test
        @DisplayName("Тест для deleteNode несуществующей заметки")
        void shouldDeleteNotExistNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            boolean deleteStatus = noteService.deleteNote(5);

            //Assert
            assertThat(deleteStatus).isFalse();
        }
    }

    @Nested
    @DisplayName("Тесты поиска заметок")
    class FindNoteTests {

        @Test
        @DisplayName("Тест для findNotesByText")
        void shouldFindNotesByText() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);

            assertThat(noteService.findNotesByText(text)).contains(addedNote);
        }

        @Test
        @DisplayName("Тест для поиска по части текста в findNotesByText")
        void shouldFindNotesByPartOfText() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);

            assertThat(noteService.findNotesByText("первой")).contains(addedNote);
        }

        @Test
        @DisplayName("Тест для поиска по тексту в другом регистре в findNotesByText")
        void shouldFindNotesByPartOfTextAndOtherCase() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);

            assertThat(noteService.findNotesByText("Первой")).contains(addedNote);
        }

        @Test
        @DisplayName("Тест для поиска по несуществующему тексту в findNotesByText")
        void shouldFindNotesByNotExistText() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "test");

            // Act
            Note addedNote = noteService.addNote(title, text, tags);

            assertThat(noteService.findNotesByText("Тест")).isEmpty();
        }

        @Test
        @DisplayName("Тест для findNotesByTags")
        void shouldFindNotesByTags() {
            Note note1 = noteService.addNote("Первая заметка", "Текст первой заметки", Set.of("java", "test"));
            Note note2 = noteService.addNote("Вторая заметка", "Текст второй заметки", Set.of("java", "code"));
            Note note3 = noteService.addNote("Третья заметка", "Текст третьей заметки", Set.of("java", "test"));

            List<Note> notes = noteService.findNotesByTags(Set.of("java"));
            assertThat(notes).hasSize(3).contains(note1, note2, note3);

            notes = noteService.findNotesByTags(Set.of("java", "test"));
            assertThat(notes).hasSize(2).contains(note1, note3);
        }

        @Test
        @DisplayName("Тест для findNotesByTags с несуществующими тегами")
        void shouldFindNotesByNotExistTags() {
            Note note1 = noteService.addNote("Первая заметка", "Текст первой заметки", Set.of("java", "test"));
            Note note2 = noteService.addNote("Вторая заметка", "Текст второй заметки", Set.of("java", "code"));
            Note note3 = noteService.addNote("Третья заметка", "Текст третьей заметки", Set.of("java", "test"));

            List<Note> notes = noteService.findNotesByTags(Set.of("java", "check"));
            assertThat(notes).isEmpty();

            notes = noteService.findNotesByTags(Set.of("check", "type"));
            assertThat(notes).isEmpty();

            notes = noteService.findNotesByTags(new HashSet<>());
            assertThat(notes).isEmpty();
        }
    }

    @Nested
    @DisplayName("Тесты работы с тегами")
    class TagTests {
        @Test
        @DisplayName("Тест для getAllTags")
        void shouldGetAllTags() {
            Note note1 = noteService.addNote("Первая заметка", "Текст первой заметки", Set.of("java", "test"));
            Note note2 = noteService.addNote("Вторая заметка", "Текст второй заметки", Set.of("java", "code"));
            Note note3 = noteService.addNote("Третья заметка", "Текст третьей заметки", Set.of("java", "test"));

            Set<String> tags = noteService.getAllTags();
            assertThat(tags).contains("java", "test", "code");

            noteService.deleteNote(note1.getId());
            noteService.deleteNote(note2.getId());
            noteService.deleteNote(note3.getId());

            tags = noteService.getAllTags();
            assertThat(tags).isEmpty();
        }
    }
}