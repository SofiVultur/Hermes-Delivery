package Classes;

import Functionalities.OwnerService;

import java.util.List;

public class Owner extends User{

    public Owner()
    {
        super();
    }

    public Owner(Long id, String name, String email, String password, String phoneNumber, List<Restaurant> restaurants) {
        super(id, name, email, password, phoneNumber);
        var owners = OwnerService.getOwnersByEmail();
        owners.put(this.email, this);
        OwnerService.setOwnersByEmail(owners);
    }

    public static class Builder {

        private Owner owner = new Owner();

        public Owner build() {
            var owners = OwnerService.getOwnersByEmail();
            owners.put(owner.email, owner);
            OwnerService.setOwnersByEmail(owners);
            return this.owner;
        }

        public Builder(String email, String password) {
            owner.email = email;
            owner.password = password;
        }

        public Builder withName(String name) {
            owner.name = name;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            owner.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withId(Long id)
        {
            owner.id = id;
            return this;
        }
    }


//    public Owner clone() throws CloneNotSupportedException
//    {
//        Owner owner = (Owner) super.clone();
//        List<Restaurant> rest = new ArrayList<>();
//        for(var r : this.restaurants)
//        {
//            rest.add(r.clone());
//        }
//        owner.restaurants=rest;
//        return owner;
//    }


    @Override
    public String toString() {
        return "Owner{" +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", id=" + id +
            '}';
    }
}
