
import Client.GatewayServiceClient;
import DataContracts.Address.BillingAddress;
import DataContracts.Address.BuyerAddress;
import DataContracts.Address.DeliveryAddress;
import DataContracts.BoletoTransaction.BoletoTransaction;
import DataContracts.BoletoTransaction.BoletoTransactionOptions;
import DataContracts.CreditCardTransaction.CreditCard;
import DataContracts.CreditCardTransaction.CreditCardTransaction;
import DataContracts.CreditCardTransaction.CreditCardTransactionOptions;
import DataContracts.Merchant.Merchant;
import DataContracts.Order.Order;
import DataContracts.Person.Buyer;
import DataContracts.Sale.CreateSaleRequest;
import DataContracts.Sale.CreateSaleResponse;
import DataContracts.Sale.RequestData;
import DataContracts.SaleOptions;
import DataContracts.ShoppingCart.ShoppingCart;
import DataContracts.ShoppingCart.ShoppingCartItem;
import EnumTypes.AddressTypeEnum;
import EnumTypes.BuyerCategoryEnum;
import EnumTypes.CountryEnum;
import EnumTypes.CreditCardBrandEnum;
import EnumTypes.CreditCardOperationEnum;
import EnumTypes.CurrencyIsoEnum;
import EnumTypes.DocumentTypeEnum;
import EnumTypes.EcommerceCategoryEnum;
import EnumTypes.EmailTypeEnum;
import EnumTypes.GenderEnum;
import EnumTypes.PersonTypeEnum;
import Utility.HttpResponseGenerics;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author munir
 */
public class AntiFraudRequest {

    public static String getResponse() {
        try {
            // Define loja
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria objeto do endereço
            BillingAddress billingAddress = new BillingAddress();
            billingAddress.setCity("Tatooine");
            billingAddress.setComplement("");
            billingAddress.setCountry(CountryEnum.Brazil);
            billingAddress.setDistrict("Mos Eisley");
            billingAddress.setNumber("123");
            billingAddress.setState("RJ");
            billingAddress.setStreet("Mos Eisley Cantina");
            billingAddress.setZipCode("20001000");

            // Cria objeto de endereço do Buyer
            BuyerAddress buyerAddress = new BuyerAddress();
            buyerAddress.setAddressType(AddressTypeEnum.Residential);
            buyerAddress.setCity("Tatooine");
            buyerAddress.setComplement("");
            buyerAddress.setCountry(CountryEnum.Brazil);
            buyerAddress.setDistrict("Mos Eisley");
            buyerAddress.setNumber("123");
            buyerAddress.setState("RJ");
            buyerAddress.setStreet("Mos Eisley Cantina");
            buyerAddress.setZipCode("20001000");

            // Cria coleção de endereços do buyer
            ArrayList<BuyerAddress> buyerAddressCollection = new ArrayList<BuyerAddress>();
            buyerAddressCollection.add(buyerAddress);

            // Cria objeto do buyer
            Buyer buyer = new Buyer();
            buyer.setAddressCollection(buyerAddressCollection);
            buyer.setBirthdate(Date.valueOf("1990-12-11"));
            buyer.setBuyerReference("C3PO");
            buyer.setDocumentNumber("12345678901");
            buyer.setDocumentType(DocumentTypeEnum.CPF);
            buyer.setEmail("lskywalker@r2d2.com");
            buyer.setEmailType(EmailTypeEnum.Personal);
            buyer.setGender(GenderEnum.M);
            buyer.setHomePhone("(21)123456789");
            buyer.setMobilePhone("(21)987654321");
            buyer.setName("Luke Skywalker");
            buyer.setPersonType(PersonTypeEnum.Person);
            buyer.setWorkPhone("(21)28467902");

            // Cria um cartão de crédito e define endereço de cobrança
            CreditCard creditCard = new CreditCard();
            creditCard.setBillingAddress(billingAddress);
            creditCard.setCreditCardBrand(CreditCardBrandEnum.Visa);
            creditCard.setCreditCardNumber("4111111111111111");
            creditCard.setExpMonth(10);
            creditCard.setExpYear(22);
            creditCard.setHolderName("LUKE SKYWALKER");
            creditCard.setSecurityCode("123");

            // Cria a transação de cartão de crédito e define cartão criado anteriormente
            CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
            creditCardTransaction.setAmountInCents(10000L);
            creditCardTransaction.setCreditCard(creditCard);
            creditCardTransaction.setInstallmentCount(1);

            // Cria item o shopping cart
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setDescription("Red Lightsaber");
            shoppingCartItem.setDiscountAmountInCents(0L);
            shoppingCartItem.setItemReference("NumeroDoProduto");
            shoppingCartItem.setName("Lightsaber");
            shoppingCartItem.setQuantity(1);
            shoppingCartItem.setTotalCostInCents(18000);
            shoppingCartItem.setUnitCostInCents(0);

            // Cria shopping cart
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setDeliveryAddress(new DeliveryAddress());
            shoppingCart.getDeliveryAddress().setCity("Galaxy Far Far Away");
            shoppingCart.getDeliveryAddress().setComplement("Bridge");
            shoppingCart.getDeliveryAddress().setCountry(CountryEnum.Brazil);
            shoppingCart.getDeliveryAddress().setDistrict("Command Room");
            shoppingCart.getDeliveryAddress().setNumber("321");
            shoppingCart.getDeliveryAddress().setState("RJ");
            shoppingCart.getDeliveryAddress().setStreet("Death Star");
            shoppingCart.getDeliveryAddress().setZipCode("10002000");
            shoppingCart.setFreightCostInCents(2000);
            shoppingCart.setShoppingCartItemCollection(new ArrayList<>());
            shoppingCart.getShoppingCartItemCollection().add(shoppingCartItem);

            // Cria o objeto order para adicionar o Order Reference
            Order order = new Order();
            order.setOrderReference("NúmeroDoPedido");

            // Cria o Sale Request para enviar o objeto de request
            CreateSaleRequest createSaleRequest = new CreateSaleRequest();
            createSaleRequest.setCreditCardTransactionCollection(new ArrayList<>());
            createSaleRequest.getCreditCardTransactionCollection().add(creditCardTransaction);
            createSaleRequest.setOptions(new SaleOptions());
            createSaleRequest.getOptions().setIsAntiFraudEnabled(Boolean.TRUE);
            createSaleRequest.setOrder(order);
            createSaleRequest.setShoppingCartCollection(new ArrayList<>());
            createSaleRequest.getShoppingCartCollection().add(shoppingCart);

            // Cria o cliente que vai enviar a transação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            // Submete a transação e retorna a resposta do gateway
            HttpResponseGenerics<CreateSaleResponse, CreateSaleRequest> httpResponse
                    = serviceClient.getSale().Create(createSaleRequest);

            return httpResponse.getRawResponse();

        } catch (Exception ex) {
            return "Error\n" + ex.toString() + "\n" + ex.getMessage();
        }
    }
}
