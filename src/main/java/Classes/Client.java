package Classes;

import Functionalities.AddressService;
import Functionalities.CartService;
import Functionalities.ClientService;

public class Client extends User{
    public Cart cart;
    public Address address;

    public Client(Long id, String name, String email, String password, String phoneNumber) {
        super(id, name, email, password, phoneNumber);
    }
    public Client() {
        var clients = ClientService.getClients();
        clients.add(this);
        ClientService.setClients(clients);
    }
    public Client(Long id, String name, String email, String password, String phoneNumber, Long cart_id, Long address_id)
    {
        super(id, name, email, password, phoneNumber);
        this.cart = CartService.getCartsById().get(cart_id);
        this.address = AddressService.getAddressById().get(address_id);
        var clients = ClientService.getClientsByEmail();
        clients.put(email, this);
        ClientService.setClientsByEmail(clients);
    }

    public static class Builder {

        private Client client = new Client();

        public Client build()
        {
            this.client.cart = new Cart();
            var clients = ClientService.getClientsByEmail();
            clients.put(client.email, client);
            ClientService.setClientsByEmail(clients);
            return this.client;
        }

        public Builder(String email, String password) {
            client.email = email;
            client.password = password;
        }


        public Builder withName(String name) {
            client.name = name;
            return this;
        }


        public Builder withPhoneNumber(String phoneNumber) {
            client.phoneNumber = phoneNumber;
            return this;
        }


        public Builder withAddress(Address address)
        {
            client.address=address;
            return this;
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", id=" + id +
            '}';
    }
}
