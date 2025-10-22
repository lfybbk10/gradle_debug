package ru.mentee.power.tdd.notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/** Сервис для работы с заметками. */
public class NoteService {

  private final Map<Integer, Note> notes = new HashMap<>();

  private final AtomicInteger nextId = new AtomicInteger(1);

  // --- МЕТОДЫ ДЛЯ РЕАЛИЗАЦИИ ЧЕРЕЗ TDD --- //

  /**
   * Добавляет новую заметку.
   *
   * @param title Заголовок.
   * @param text Текст.
   * @param tags Набор тегов (может быть null).
   * @return Созданная заметка с присвоенным ID.
   */
  public Note addNote(String title, String text, Set<String> tags) {
    int id = nextId.getAndIncrement();
    Note note = new Note(id, title, text);
    if (tags != null) {
      tags.forEach(note::addTag);
    }
    notes.put(id, note);
    return note;
  }

  /**
   * Получает заметку по ID.
   *
   * @param id ID заметки.
   * @return Optional с заметкой, если найдена, иначе Optional.empty().
   */
  public Optional<Note> getNoteById(int id) {
    if (notes.containsKey(id)) {
      return Optional.of(notes.get(id));
    }
    return Optional.empty();
  }

  /**
   * Получает все заметки.
   *
   * @return Неизменяемый список всех заметок.
   */
  public List<Note> getAllNotes() {
    return List.copyOf(notes.values());
  }

  /**
   * Обновляет заголовок и текст существующей заметки.
   *
   * @param id ID заметки.
   * @param newTitle Новый заголовок.
   * @param newText Новый текст.
   * @return true, если заметка найдена и обновлена, иначе false.
   */
  public boolean updateNoteText(int id, String newTitle, String newText) {
    if (notes.containsKey(id)) {
      Note note = notes.get(id);
      note.setTitle(newTitle);
      note.setText(newText);
      return true;
    }
    return false;
  }

  /**
   * Добавляет тег к существующей заметке.
   *
   * @param id ID заметки.
   * @param tag Тег для добавления.
   * @return true, если заметка найдена и тег добавлен, иначе false.
   */
  public boolean addTagToNote(int id, String tag) {
    if (notes.containsKey(id)) {
      Note note = notes.get(id);
      if (!note.getTags().contains(tag)) {
        note.addTag(tag);
        return true;
      }
    }
    return false;
  }

  /**
   * Удаляет тег у существующей заметки.
   *
   * @param id ID заметки.
   * @param tag Тег для удаления.
   * @return true, если заметка найдена и тег удален, иначе false.
   */
  public boolean removeTagFromNote(int id, String tag) {
    if (notes.containsKey(id)) {
      Note note = notes.get(id);
      if (note.getTags().contains(tag)) {
        note.removeTag(tag);
        return true;
      }
    }
    return false;
  }

  /**
   * Удаляет заметку по ID.
   *
   * @param id ID заметки.
   * @return true, если заметка найдена и удалена, иначе false.
   */
  public boolean deleteNote(int id) {
    if (notes.containsKey(id)) {
      notes.remove(id);
      return true;
    }
    return false;
  }

  /**
   * Ищет заметки, содержащие текст (без учета регистра).
   *
   * @param query Текст для поиска.
   * @return Список найденных заметок.
   */
  public List<Note> findNotesByText(String query) {
    List<Note> result = new ArrayList<>();
    query = query.toLowerCase();
    for (Note note : notes.values()) {
      if (note.getTitle().toLowerCase().contains(query)
          || note.getText().toLowerCase().contains(query)) {
        result.add(note);
      }
    }
    return result;
  }

  /**
   * Ищет заметки, содержащие ВСЕ указанные теги (без учета регистра).
   *
   * @param searchTags Набор тегов для поиска.
   * @return Список найденных заметок.
   */
  public List<Note> findNotesByTags(Set<String> searchTags) {
    if (searchTags.isEmpty()) {
      return new ArrayList<>();
    }

    Set<String> normalizedTags = new HashSet<>();
    for (String tag : searchTags) {
      normalizedTags.add(tag.toLowerCase());
    }

    List<Note> result = new ArrayList<>();
    for (Note note : notes.values()) {
      if (note.getTags().containsAll(normalizedTags)) {
        result.add(note);
      }
    }

    return result;
  }

  /**
   * Получает список всех уникальных тегов из всех заметок.
   *
   * @return Список уникальных тегов (в нижнем регистре).
   */
  public Set<String> getAllTags() {
    HashSet<String> result = new HashSet<>();
    for (Note note : notes.values()) {
      result.addAll(note.getTags());
    }
    return result;
  }
}
