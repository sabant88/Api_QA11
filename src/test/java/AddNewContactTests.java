import com.google.gson.Gson;
import dto.ContactDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewContactTests {

    static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";



    @Test
    public void addNewContact() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        ContactDto contactDto = ContactDto.builder()
                .address("pakistan")
                .description("paki")
                .email("paki"+i+"@mail.ru")
                .lastName("abed")
                .name("mister")
                .id(0)
                .phone("1"+i)
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto), LoginTests.JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization",token)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        ContactDto addContact=gson.fromJson(response.body().string(),ContactDto.class);
        System.out.println(addContact);

    }



}
