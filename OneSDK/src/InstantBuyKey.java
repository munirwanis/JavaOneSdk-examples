
import Client.GatewayServiceClient;
import DataContracts.InstantBuy.GetInstantBuyDataResponse;
import Utility.HttpResponseGenericResponse;
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
public class InstantBuyKey {
    public static String getResponse() {
        try {
            // Define loja
            UUID merchantKey = UUID.fromString("85328786-8BA6-420F-9948-5352F5A183EB"); // Chave da Loja - MerchantKey
            
            // Define o InstantBuyKey
            UUID instantBuyKey = UUID.fromString("3b3b5b62-6660-428d-905e-96f49d46ae28");
            
            // Cria o cliente que vai enviar a transação
            GatewayServiceClient serviceClient = new GatewayServiceClient(merchantKey, "https://sandbox.mundipaggone.com");

            // Submete a transação e retorna a resposta do gateway
            HttpResponseGenericResponse<GetInstantBuyDataResponse> httpResponse
                    = serviceClient.getCreditCard().GetInstantBuyData(instantBuyKey);
            
            return httpResponse.getRawResponse();

        } catch (Exception ex) {
            return "Error";
        }

    }
}
