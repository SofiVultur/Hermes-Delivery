package Classes;

import java.util.concurrent.atomic.AtomicLong;

public class User {
    protected static AtomicLong ID = new AtomicLong(0);
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected Long id;

    protected static Long newID()
    {
        System.out.println(ID);
        return ID.incrementAndGet();
    }

    public User()
    {
        this.id = newID();
    }

    public User(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.id = newID();
    }

    public User(Long id, String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
