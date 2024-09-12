package br.com.syonet.view;

import java.util.List;
import java.util.Scanner;

import br.com.syonet.model.Student;
import br.com.syonet.service.StudentService;

public class StudentView {

  private int selectedOption;
  private boolean exit;
  private Scanner scanner;

  private StudentService service;

  public StudentView(StudentService service, Scanner scanner) {
    this.service = service;
    this.scanner = scanner;
  }

  public void init() {
    System.out.println("Ola seja vem vindo ao nosso cadastro de estudantes.");
  }

  public void showOptions() {
    System.out.println("Por favor selecione uma operaçoes abaixo:");
    System.out.println();
    System.out.println("\t(1) - criar novo estudantes");
    System.out.println("\t(2) -Listar estudantes");
    System.out.println("\t(3) - atualizar estudantes");
    System.out.println("\t(4) - deletar estudantes");
    System.out.println("\t(5) - Listar por id estudantes");
    System.out.println("\t(6) - Listar por nome estudantes");

    System.out.println("\t(0) - sair");
  }

  public Integer getSelectedOption() {
    return selectedOption;
  }

  public boolean isExit() {
    return this.exit;
  }

  public void readSelectedOption() {
    String nextLine = this.scanner.nextLine();
    int answer = Integer.parseInt(nextLine);
    this.exit = answer == 0;
    this.selectedOption = answer;
  }

  public void executeSelectedOperation() {
    switch (this.selectedOption) {
      case 1:
        this.initCreationProcess();
        break;
      case 2:
        this.initListProcess();
        break;
      case 3:
        this.initUpdateProcess();

        break;
      case 4:
        this.initDeleteProcess();

        break;
      case 5:
        this.initFindByIdProcess();
        break;
      case 6:
      this.initFindNameProcess();
      break;

      default:
        break;
    }
  }

  private void initListProcess() {
    List<Student> students = this.service.listAll();
    if (students != null && !students.isEmpty()) {
      System.out.println();
      System.out.println("\t\tid\t\t|\t\tnome\t\t|\t\tidade\t\t|\t\temail");
      for (int i = 0; i < students.size(); i++) {
        Student student = students.get(i);
        System.out.println("\t\t%d\t\t\t\t%s\t\t\t\t%d\t\t\t\t%s".formatted(
          student.getId(),
          student.getName(),
          student.getAge(),
          student.getEmail()));
      }
      System.out.println();
    } else {
      System.out.println("Não há estudantes cadastrados!");
    }
  }

  private void initCreationProcess() {
    System.out.println("Ok, qual é o nome do estudante?");
    String name = this.scanner.nextLine();
    System.out.println("E o email do rapaz ou da moça?");
    String email = this.scanner.nextLine();
    System.out.println("Muito bom! agora qual a idade dela ou dele?");
    Integer idade = Integer.parseInt(this.scanner.nextLine());
    System.out.println("Obrigado temos todas as info, criando novo estudante!");
    Student studant = new Student(name, idade, email);
    long id = this.service.save(studant);
    System.out.println("O id do novo estudante é " + id);
  }

  private void initUpdateProcess() {
    System.out.println("Digite o ID do estudante para atualizar:");
    long id = Long.parseLong(this.scanner.nextLine());
    Student student = this.service.findById(id);
    if (student != null) {
        System.out.println("Digite o novo nome (pressione enter para manter o valor atual):");
        String name = this.scanner.nextLine();
        if (!name.isEmpty()) {
            student = student.setName(name);
        }
        System.out.println("Digite o novo email (pressione enter para manter o valor atual):");
        String email = this.scanner.nextLine();
        if (!email.isEmpty()) {
            student = student.setEmail(email);
        }
        System.out.println("Digite a nova idade (pressione enter para manter o valor atual):");
        String idadeStr = this.scanner.nextLine();
        if (!idadeStr.isEmpty()) {
            Integer idade = Integer.parseInt(idadeStr);
            student = student.setAge(idade);
        }
        this.service.update(student);
        System.out.println("Estudante atualizado com sucesso!");
    } else {
        System.out.println("Estudante não encontrado!");
    }
}

  private void initDeleteProcess() {
    System.out.println("Digite o ID do estudante para deletar:");
    long id = Long.parseLong(this.scanner.nextLine());
    this.service.delete(id);
    System.out.println("Estudante deletado com sucesso!");
  }

  private void initFindByIdProcess() {
    System.out.println("Digite o ID do estudante para buscar:");
    long id = Long.parseLong(this.scanner.nextLine());
    Student student = this.service.findById(id);
    if (student != null) {
        System.out.println();
        System.out.println("\t\tid\t\t|\t\tnome\t\t|\t\tidade\t\t|\t\temail");
        System.out.println("\t\t%d\t\t|\t\t%s\t\t|\t\t%d\t\t|\t\t%s".formatted(
            student.getId(),
            student.getName(),
            student.getAge(),
            student.getEmail()));
        System.out.println();
    } else {
        System.out.println("Estudante não encontrado!");
    }
}


  private void initFindNameProcess() {
    System.out.println("Digite o nome do estudante para buscar:");
    String name = this.scanner.nextLine();
    List<Student> students = this.service.findStudentByName(name);
    if (students != null && !students.isEmpty()) {
        System.out.println();
        System.out.println("\t\tid\t\t|\t\tnome\t\t|\t\tidade\t\t|\t\temail");
        for (Student student : students) {
            System.out.println("\t\t%d\t\t\t\t%s\t\t\t\t%d\t\t\t\t%s".formatted(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getEmail()));
        }
        System.out.println();
    } else {
        System.out.println("Não há estudantes com esse nome!");
    }
}

}
