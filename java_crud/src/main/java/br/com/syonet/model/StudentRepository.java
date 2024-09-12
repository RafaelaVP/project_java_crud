package br.com.syonet.model;

import java.util.List;

public interface StudentRepository {
  Student create(Student student);

  List<Student> listAll();

  Student update(Student student);

  Student findById(long id);

  Student delete(long id);

  List<Student> findStudentByName(String name);

}
