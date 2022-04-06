import com.google.gson.Gson;
import dto.ContactDto;
import dto.DeleteByIdResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByID {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBn" +
            "bWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    int id;


    @BeforeMethod
    public void addNewContactForDelete() throws IOException {
        //add new contact
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        ContactDto contactDto = ContactDto.builder()
                .address("pakistan")
                .description("paki")
                .email("paki@mail.ru")
                .lastName("abed")
                .name("mister")
                .id(0)
                .phone("108")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto), LoginTests.JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization",token)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        ContactDto addContact=gson.fromJson(response.body().string(),ContactDto.class);
        System.out.println(addContact.getId());
        id= addContact.getId();


    }

    @Test
    public void deleteByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/"+id)
                .delete()
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        DeleteByIdResponseDto delete=  gson.fromJson(response.body().string(), DeleteByIdResponseDto.class);
        System.out.println(delete.getStatus());
        Assert.assertEquals(delete.getStatus(),"Contact was deleted!");
    }
}
