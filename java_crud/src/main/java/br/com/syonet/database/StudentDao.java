package br.com.syonet.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.syonet.model.Student;
import br.com.syonet.model.StudentRepository;

public class StudentDao implements StudentRepository {
  private final Logger log = Logger.getLogger(this.getClass().getName());
  private Connection connection;

	public StudentDao(Connection connection) {
    this.connection = connection;
  }

	@Override
	public Student create(Student student) {
    String sql = "insert into students (name, age, email) values (?, ?, ?);";
    try (PreparedStatement prst = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			prst.setString(1, student.getName());
      prst.setInt(2, student.getAge());
      prst.setString(3, student.getEmail());
      prst.executeUpdate();
      try (ResultSet resultSet = prst.getGeneratedKeys()) {
        resultSet.next();
        int id = resultSet.getInt(1);
        return new Student(id, student.getName(), student.getAge(), student.getEmail());
      }
		} catch (SQLException e) {
			log.warning(e.getMessage());
      throw new RuntimeException(e);
		}
	}

  @Override
  public List<Student> listAll() {
    try (Statement st = this.connection.createStatement()) {
      st.execute("SELECT id, name, age, email FROM students;");
      List<Student> result = new ArrayList<>();
      try (ResultSet rs = st.getResultSet()) {
        while (rs.next()) {
          var id = rs.getInt("id");
          var name = rs.getString("name");
          var age = rs.getInt("age");
          var email = rs.getString("email");
          result.add(new Student(id, name, age, email));
        }
      }
      return result;
    } catch (SQLException e) {
      return Collections.emptyList();
    }
  }

  @Override
  public Student update(Student student) {
      String sql = "UPDATE students SET name = ?, age = ?, email = ? WHERE id = ?;";
      try (PreparedStatement prst = this.connection.prepareStatement(sql)) {
          prst.setString(1, student.getName());
          prst.setInt(2, student.getAge());
          prst.setString(3, student.getEmail());
          prst.setLong(4, student.getId());
          int rowsAffected = prst.executeUpdate();
          
          if (rowsAffected == 0) {
              throw new IllegalArgumentException("Estudante com o ID fornecido não encontrado.");
          }
          
          return student;
      } catch (SQLException e) {
          log.warning("Erro ao atualizar o estudante: " + e.getMessage());
          throw new RuntimeException(e);
      }
  }

  @Override
  public Student findById(long id) {
      String sql = "SELECT id, name, age, email FROM students WHERE id = ?;";
      try (PreparedStatement prst = this.connection.prepareStatement(sql)) {
          prst.setLong(1, id);
          try (ResultSet rs = prst.executeQuery()) {
              if (rs.next()) {
                  var name = rs.getString("name");
                  var age = rs.getInt("age");
                  var email = rs.getString("email");
                  return new Student(id, name, age, email);
              } else {
                  return null;
              }
          }
      } catch (SQLException e) {
          log.warning(e.getMessage());
          throw new RuntimeException(e);
      }
    }
    @Override
    public Student delete(long id) {
        String sql = "SELECT id, name, age, email FROM students WHERE id = ?;";
        Student student = null;
        try (PreparedStatement prst = this.connection.prepareStatement(sql)) {
            prst.setLong(1, id);
            try (ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    student = new Student(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw new RuntimeException(e);
        }
        if (student != null) {
            sql = "DELETE FROM students WHERE id = ?;";
            try (PreparedStatement prst = this.connection.prepareStatement(sql)) {
                prst.setLong(1, id);
                prst.executeUpdate();
            } catch (SQLException e) {
                log.warning(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return student;
    }

    @Override
    public List<Student> findStudentByName(String name) {
        String sql = "SELECT id, name, age, email FROM students WHERE name = ?;";
        List<Student> students = new ArrayList<>();
        try (PreparedStatement prst = this.connection.prepareStatement(sql)) {
            prst.setString(1, name);
            try (ResultSet rs = prst.executeQuery()) {
                while (rs.next()) {
                    var id = rs.getLong("id");
                    var age = rs.getInt("age");
                    var email = rs.getString("email");
                    students.add(new Student(id, name, age, email));
                }
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw new RuntimeException(e);
        }
        return students; 
    }  
  
}
