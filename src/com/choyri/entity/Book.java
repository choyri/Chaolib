package com.choyri.entity;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private String author;
    private int type;
    private int amount;
    private String price;
    private String intro;

    /**
     * 特殊属性 JSP 中使用
     * 图书类别名称
     */
    private String typeForJSP;

    public Book() {
    }

    public Book(String isbn, String title, String author, int type, int amount, String price, String intro) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTypeForJSP() {
        return typeForJSP;
    }

    public void setTypeForJSP(String typeForJSP) {
        this.typeForJSP = typeForJSP;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author + ", type=" + type
                + ", amount=" + amount + ", price=" + price + ", intro=" + intro + ", typeForJSP=" + typeForJSP + "]";
    }
}