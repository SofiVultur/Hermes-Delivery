package Classes;

import java.util.List;

public class Driver extends User{
    public Driver()
    {
        super();
    }

    public Driver(Long id, String name, String email, String password, String phoneNumber, List<Restaurant> restaurants) {
        super(id, name, email, password, phoneNumber);

    }

    public static class Builder {

        private Driver driver = new Driver();

        public Driver build() {
            return this.driver;
        }

        public Builder(String email, String password) {
            driver.email = email;
            driver.password = password;
        }

        public Builder withName(String name) {
            driver.name = name;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            driver.phoneNumber = phoneNumber;
            return this;
        }
    }
}
