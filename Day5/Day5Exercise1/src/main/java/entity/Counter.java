/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Lovro
 */
public class Counter {
    
    int count = 0;
    
    public int getValue() {
    return count;
  }
  
  public synchronized void incr() {
    count++;
  }
}
