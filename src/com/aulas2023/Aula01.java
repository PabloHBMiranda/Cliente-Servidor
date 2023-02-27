package com.aulas2023;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Aula01 {
    private JPanel painelPrincipal;
    private JButton botaoMsg;
    private JTextField nome;
    private JCheckBox idade;
    private JComboBox curso;
    private JRadioButton sexo_m;
    private JRadioButton sexo_f;
    private JTextField dataNascimento;
    private JComboBox civil;
    private JTextField prof;
    private JTextField cpf;

    public Aula01() {
        botaoMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.err.println("Teste 01");
//                JOptionPane.showMessageDialog(null,"Teste 01");
                String cpfString = cpf.getText();
                if(nome.getText().isEmpty()){
                    JOptionPane.showMessageDialog(painelPrincipal, "Nome não pode estar vazio!", "ERRO", JOptionPane.ERROR_MESSAGE);
                } else if( !validarCPF(cpfString)){
                    JOptionPane.showMessageDialog(painelPrincipal, "CPF inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
                } else if(dataNascimento.getText().isEmpty() || !validarData(dataNascimento.getText())){
                    JOptionPane.showMessageDialog(painelPrincipal, "Data de Nascimento Inválida", "ERRO", JOptionPane.ERROR_MESSAGE);
                }else{
                    String profissao = prof.getText() + ".";
                    String sexo = "";
                    String estado_civil = "";
                    String data = dataNascimento.getText();
                    int idadeNascimento = calcularIdade(data);
                    if(sexo_m.isSelected()) sexo = "Masculino";
                    else if(sexo_f.isSelected()) sexo = "Feminino";
                    else sexo = "Não informado";
                    if(prof.getText().isEmpty()) profissao = "Desempregado.";
                    else if(prof.getText().equals("Engenheiro") || prof.getText().equals("Analista de Sistema")) profissao += "\nVagas Disponíveis!";
                    if(civil.getSelectedIndex() == 1) estado_civil = "Casado(a)";
                    else if(civil.getSelectedIndex() == 2) estado_civil = "Solteiro(a)";
                    else if(civil.getSelectedIndex() == 3) estado_civil= "Divorciado(a)";
                    else if(civil.getSelectedIndex() == 4) estado_civil = "Viuvo(a)";
                    else estado_civil = "Não informado";
                    System.err.println("Nome: " + nome.getText() + "\n" + ".\n Sexo " + sexo + ".\nProfissão: " + profissao);
                    JOptionPane.showMessageDialog(null,
                            "Nome: " + nome.getText() +  ".\nSexo: " + sexo +
                                    ".\nIdade: " + idadeNascimento + " ano(s)." + "\nEstado Civil: " + estado_civil + ".\nProfissão: " + profissao);
                }
            }
        });
    }

    public static void main(String[] args){
        JFrame quadro = new JFrame("Aula01");
        quadro.setContentPane(new Aula01().painelPrincipal);
        quadro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quadro.pack();
        quadro.setVisible(true);
    }
    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) {
            primeiroDigito = 0;
        }
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito > 9) {
            segundoDigito = 0;
        }
       if(Character.getNumericValue(cpf.charAt(9)) == primeiroDigito
                && Character.getNumericValue(cpf.charAt(10)) == segundoDigito){
            return true;
       }
       else {
           return false;
       }

    }

    public static boolean validarData(String data) {
        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(data);
            Date now = new Date();
            return !date.after(now);
        } catch (ParseException e) {
            return false;
        }
    }

    public static int calcularIdade(String dataNascimento) {
        String[] partes = dataNascimento.split("/");
        if (partes.length != 3) {
            return -1; // retorna -1 se a data de nascimento não for válida
        }

        int dia, mes, ano;
        try {
            dia = Integer.parseInt(partes[0]);
            mes = Integer.parseInt(partes[1]);
            ano = Integer.parseInt(partes[2]);
        } catch (NumberFormatException e) {
            return -1; // retorna -1 se a data de nascimento não for válida
        }

        LocalDate dataNascimentoLocal = LocalDate.of(ano, mes, dia);
        LocalDate dataAtual = LocalDate.now();
        return Period.between(dataNascimentoLocal, dataAtual).getYears();
    }
}
