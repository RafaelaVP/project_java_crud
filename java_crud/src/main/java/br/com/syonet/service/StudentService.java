package br.com.syonet.service;

import java.util.List;

import br.com.syonet.model.Student;
import br.com.syonet.model.StudentRepository;

public class StudentService {
  private StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public long save(Student student) {
    if (student.getId() == 0) {
      Student newStudant = this.repository.create(student);
      return newStudant.getId();
    }
    throw new IllegalArgumentException("Id não pode ser atribuido");
  }

  public List<Student> listAll() {
    return this.repository.listAll();
  }

  public void update(Student student) {
    this.repository.update(student);
  }

  public Student findById(long id) {
    return this.repository.findById(id);
  }

  public void delete(long id) {
    this.repository.delete(id);
  }

  public List<Student> findStudentByName(String name) {
    return this.repository.findStudentByName(name);
  }

}
