package com.luo.common.utils.httpUtils;

import lombok.Data;

@Data
public class SsoUtil {
    private String client_id;
    private String response_type;
    private String response_mode;
    private String scope;
    private String redirect_uri;
    private String state;
    private String nonce;
    public static String getTokenSsoUri(String getTokenUri,String client_id,String response_type,String response_mode,String scope,String redirect_uri,String state,String nonce){
        return getTokenUri +
                "?client_id=" + client_id +
                "&response_type=" + response_type +
                "&response_mode=" + response_mode +
                "&scope=" + scope +
                "&redirect_uri=" + redirect_uri +
                "&state=" + state +
                "&nonce=" + nonce;
    }

}
