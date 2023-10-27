package com.example.hkit.dto;

import com.example.hkit.entity.Account;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AccountDTO {
    private String name;
    private String accountID;
    private String accountPW;
    private String email;
    private Boolean hidden;


    public static AccountDTO toDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName(account.getName());
        accountDTO.setAccountPW(accountDTO.getAccountPW());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setHidden(account.getHidden());

        return accountDTO;
    }

    public static JsonObject toJson(Account account) {
        JsonObject json = new JsonObject();
        json.addProperty("id", account.getId());
        json.addProperty("name", account.getName());
        json.addProperty("accountID", account.getAccountID());
        json.addProperty("hidden", account.getHidden());
        json.addProperty("created_at", account.getCreated_at().toString());
        return json;
    }
}
