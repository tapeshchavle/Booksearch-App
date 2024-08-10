/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package books;

/**
 *
 * @author hp
 */
public class Book {

    public String getBookname() {
        return bookname;
    }

    public int getPrice() {
        return price;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    private String bookname;
    private int price;
    
    
    
}
