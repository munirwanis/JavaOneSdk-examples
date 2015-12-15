
import Client.GatewayServiceClient;
import DataContracts.CreditCardTransaction.ManageCreditCardTransaction;
import DataContracts.Sale.ManageSaleRequest;
import DataContracts.Sale.ManageSaleResponse;
import EnumTypes.ManageOperationEnum;
import Utility.HttpResponseGenerics;
import java.util.ArrayList;
import java.util.List;
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
public class Capture {

    public static String getResponseWithOrderKey() {
        try {
            // Define loja 
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria o cliente que vai efetuar a operação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            // Define a chave do pedido
            UUID orderKey = UUID.fromString("219d7581-78e2-4aa9-b708-b7c585780bfc"); // Chave do pedido

            // Submete a requisição
            HttpResponseGenerics<ManageSaleResponse, ManageSaleRequest> httpResponse
                    = serviceClient.getSale().Manage(ManageOperationEnum.Capture, orderKey);

            return httpResponse.getRawResponse();
        } catch (Exception e) {
            return "Error";
        }
    }

    public static String getResponseWithExtras() {
        try {
            // Define loja 
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey

            // Cria o cliente que vai efetuar a operação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            ManageSaleRequest manageSaleRequest = new ManageSaleRequest();

            // Define os detalhes da transação de cartão de crédito
            ManageCreditCardTransaction manageCreditCardTransaction = new ManageCreditCardTransaction();
            manageCreditCardTransaction.setAmountInCents(10000L);
            manageCreditCardTransaction.setTransactionKey(UUID.fromString("20ba0520-7d09-44f8-8fbc-e4329e2b18d5"));

            // Cria a lista de transações de cartão e adiciona a transação criada a lista
            List<ManageCreditCardTransaction> manageCreditCardTransactionList = new ArrayList<ManageCreditCardTransaction>();
            manageCreditCardTransactionList.add(manageCreditCardTransaction);

            // Define a chave do pedido
            manageSaleRequest.setOrderKey(UUID.fromString("219d7581-78e2-4aa9-b708-b7c585780bfc"));
            manageSaleRequest.setCreditCardTransactionCollection(manageCreditCardTransactionList);

            // Submete a requisição
            HttpResponseGenerics<ManageSaleResponse, ManageSaleRequest> httpResponse
                    = serviceClient.getSale().Manage(ManageOperationEnum.Capture, manageSaleRequest);

            return httpResponse.getRawResponse();
        } catch (Exception e) {
            return "Error";
        }
    }
}
