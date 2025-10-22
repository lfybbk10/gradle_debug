package ru.mentee.power.tdd.notes;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

public class Note {

  private final int id; // Уникальный ID
  private String title; // Заголовок
  private String text; // Текст заметки
  private final LocalDate creationDate; // Дата создания
  private Set<String> tags; // Набор тегов (уникальные строки)

  public Note(int id, String title, String text) {
    if (title == null || text == null)
      throw new IllegalArgumentException("Title and text must not be null");
    this.id = id;
    this.title = title;
    this.text = text;
    this.creationDate = LocalDate.now();
    this.tags = new HashSet<>();
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getText() {
    return text;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public Set<String> getTags() {
    return Collections.unmodifiableSet(tags);
  }

  public void setTitle(String title) {
    if (title == null) throw new IllegalArgumentException("Title must not be null");
    this.title = title;
  }

  public void setText(String text) {
    if (text == null) throw new IllegalArgumentException("Text must not be null");
    this.text = text;
  }

  public void addTag(String tag) {
    if (tag == null || tag.isEmpty()) return;

    tags.add(tag.toLowerCase());
  }

  public boolean removeTag(String tag) {
    if (tags.contains(tag.toLowerCase())) {
      tags.remove(tag.toLowerCase());
      return true;
    }
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Note note)) return false;
    return id == note.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
