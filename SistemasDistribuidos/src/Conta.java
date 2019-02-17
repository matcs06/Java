/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mateus - PC
 */
public class Conta {
    String numberConta;
    String numberAgencia;
    String nomePessoa;
    float saldo;
    private String extra = "-";


    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
    public String getNumberConta() {
        return numberConta;
    }

    public void setNumberConta(String numberConta) {
        this.numberConta = numberConta;
    }

    public String getNumberAgencia() {
        return numberAgencia;
    }

    public void setNumberAgencia(String numberAgencia) {
        this.numberAgencia = numberAgencia;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    /**
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }
    
    /**
     * @param extra the extra to set
     */
    public void setExtra(String extra) {
        this.extra = extra;
    }  
}
