package com.project.railway;

import com.project.railway.data.entity.Client;
import com.project.railway.data.entity.DiscountType;
import com.project.railway.service.ClientService;
import javassist.expr.Instanceof;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void shouldReturnUser(){
        final Client client = (Client) clientService.loadUserByUsername("todor_georgiev@yahoo.com");

        assertThat(client).isNotNull();
        assertThat(client).isInstanceOf(Client.class);
        assertThat(client.getFirstName()).isEqualTo("Тодор");
        assertThat(client.getLastName()).isEqualTo("Георгиев");
    }

    @Test
    public void shouldNotReturnUser(){
        try{
            final Client client = (Client) clientService.loadUserByUsername("fake@google.com");
            assertThat(client).isNull();
        }catch(UsernameNotFoundException ex){
        }
    }

    @Test
    public void shouldValidateClientData(){
        final boolean validation = clientService.validateClientData("Petko", "Petkov", "petkov@yahoo.com", "+359895123123", Long.parseLong("2"), "123456");

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateClientData(){
        final boolean validation = clientService.validateClientData("Petko", "Petkov", "petkyahoo.com", "+359895123123", Long.parseLong("1"), "");

        assertThat(validation).isEqualTo(false);
    }

    @Test
    public void shouldValidateClientAddress(){
        final boolean validation = clientService.validateClientAddress("Test street", "test", "test", "1234");

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateClientAddress(){
        final boolean validation = clientService.validateClientAddress("T%$#@$%dsdsd", "tes$%#t", "test3##", "sddef");

        assertThat(validation).isEqualTo(false);
    }

    @Test
    public void shouldValidateClientCreditCard(){
        final boolean validation = clientService.validateCreditCard("Petko Petkov", "5544322233123456", "10", "23", "123");

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateClientCreditCard(){
        final boolean validation = clientService.validateCreditCard("Petko% Petkov", "554", "1", "2", "1##23");

        assertThat(validation).isEqualTo(false);
    }

    @Test
    public void shouldValidateClientPhoneNumber(){
        final boolean validation = clientService.validateClientPhoneNumber("+359895123456");

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateClientPhoneNumber(){
        final boolean validation = clientService.validateClientPhoneNumber("asdasd");

        assertThat(validation).isEqualTo(false);
    }

    @Test
    public void shouldValidateUser(){
        Client client = new Client();
        client.setFirstName("Petko");
        client.setLastName("Petkov");
        client.setEmail("petko@yahoo.com");
        client.setPhoneNumber("+359895123456");
        client.setPassword("1234567890");
        client.setAddress("");
        client.setAdditionalAddress("");
        client.setCity("");
        client.setZip("");
        client.setCreditCardDetails("", "", "", "", "");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("1"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateUser(){
        Client client = new Client();
        client.setFirstName("Petko%#");
        client.setLastName("Petkov%%");
        client.setEmail("petkoyahoo.com");
        client.setPhoneNumber("+3598#$%95123456");
        client.setPassword("1230");
        client.setAddress("");
        client.setAdditionalAddress("");
        client.setCity("");
        client.setZip("");
        client.setCreditCardDetails("", "", "", "", "");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("1"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(false);
    }

    @Test
    public void shouldValidateUserWithAddress(){
        Client client = new Client();
        client.setFirstName("Petko");
        client.setLastName("Petkov");
        client.setEmail("petko@yahoo.com");
        client.setPhoneNumber("+359895123456");
        client.setPassword("1234567890");
        client.setAddress("Test street");
        client.setAdditionalAddress("test");
        client.setCity("TestCity");
        client.setZip("1234");
        client.setCreditCardDetails("", "", "", "", "");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("1"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateUserWithAddress(){
        Client client = new Client();
        client.setFirstName("Petko");
        client.setLastName("Petkov");
        client.setEmail("petko@yahoo.com");
        client.setPhoneNumber("+359895123456");
        client.setPassword("1234567890");
        client.setAddress("$$@#$%");
        client.setAdditionalAddress("@#$%");
        client.setCity("2325");
        client.setZip("asdad");
        client.setCreditCardDetails("", "", "", "", "");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("1"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(false);
    }

    @Test
    public void shouldValidateUserWithAddressAndCreditCard(){
        Client client = new Client();
        client.setFirstName("Petko");
        client.setLastName("Petkov");
        client.setEmail("petko@yahoo.com");
        client.setPhoneNumber("+359895123456");
        client.setPassword("1234567890");
        client.setAddress("Test street");
        client.setAdditionalAddress("test");
        client.setCity("TestCity");
        client.setZip("1234");
        client.setCreditCardDetails("Petko Petkov", "5544322233123456", "10", "23", "123");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("1"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateUserWithAddressAndCreditCard(){
        Client client = new Client();
        client.setFirstName("Petko");
        client.setLastName("Petkov");
        client.setEmail("petko@yahoo.com");
        client.setPhoneNumber("+359895123456");
        client.setPassword("1234567890");
        client.setAddress("Test street");
        client.setAdditionalAddress("test");
        client.setCity("TestCity");
        client.setZip("1234");
        client.setCreditCardDetails("Petko%%# Petkov",
                                    "55456",
                                    "1",
                                    "20",
                                    "##4");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("1"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(false);
    }

    @Test
    public void shouldValidateUserWithAddressAndCreditCardAndDiscountDocument(){
        Client client = new Client();
        client.setFirstName("Petko");
        client.setLastName("Petkov");
        client.setEmail("petko@yahoo.com");
        client.setPhoneNumber("+359895123456");
        client.setPassword("1234567890");
        client.setAddress("Test street");
        client.setAdditionalAddress("test");
        client.setCity("TestCity");
        client.setZip("1234");
        client.setCreditCardDetails("Petko Petkov", "5544322233123456", "10", "23", "123");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("3"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("1234567");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(true);
    }

    @Test
    public void shouldNotValidateUserWithAddressAndCreditCardAndDiscountDocument(){
        Client client = new Client();
        client.setFirstName("Petko");
        client.setLastName("Petkov");
        client.setEmail("petko@yahoo.com");
        client.setPhoneNumber("+359895123456");
        client.setPassword("1234567890");
        client.setAddress("Test street");
        client.setAdditionalAddress("test");
        client.setCity("TestCity");
        client.setZip("1234");
        client.setCreditCardDetails("Petko Petkov",
                                    "5544322233123456",
                                    "10",
                                    "23",
                                    "123");
        DiscountType discountType = new DiscountType();
        discountType.setId(Long.parseLong("3"));
        client.setDiscountType(discountType);
        client.setDocumentNumber("%%$523434");

        final boolean validation = clientService.validateUser(client);

        assertThat(validation).isEqualTo(false);
    }
}

