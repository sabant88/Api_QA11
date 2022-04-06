import com.google.gson.Gson;
import dto.AuthDto;
import dto.ErrorDto;
import dto.ResponseAuthDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {

    static final MediaType JSON=MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();


    @Test
    public void loginSuccessTest() throws IOException {
        AuthDto authDto = AuthDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345$")
                .build() ;

        RequestBody body = RequestBody.create(gson.toJson(authDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        ResponseAuthDto responseAuthDto = gson.fromJson(response.body().string(),ResponseAuthDto.class);
        System.out.println("Token ----> " + responseAuthDto.getToken());

    }

    @Test
    public void unsuccessefulLoginTestWrongEmail() throws IOException {
        AuthDto authDto = AuthDto.builder()
                .email("noagmail.com")
                .password("Nnoa12345$")
                .build() ;

        RequestBody body = RequestBody.create(gson.toJson(authDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
    //    System.out.println(errorDto.getDetails());
        System.out.println(errorDto.getMessage());
    //    System.out.println(errorDto.getCode());
        Assert.assertEquals(errorDto.getMessage(),"Wrong email format! Example: name@mail.com");

    }

    @Test
    public void unregisteredUserLoginTest() throws IOException {
        AuthDto authDto = AuthDto.builder()
                .email("Dnoa@gmail.com")
                .password("Nnoa12345$")
                .build() ;

        RequestBody body = RequestBody.create(gson.toJson(authDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);

    }
}
