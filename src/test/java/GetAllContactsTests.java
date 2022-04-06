import com.google.gson.Gson;
import dto.AllContactsDto;
import dto.ContactDto;
import dto.ErrorDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsTests {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBn" +
            "bWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @Test
    public void getAllContactSuccessTest() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization",token).build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AllContactsDto all=gson.fromJson(response.body().string(), AllContactsDto.class);

        List<ContactDto> contacts = all.getContacts();
        for (ContactDto contact:contacts){
            System.out.println(contact.toString());
            System.out.println("==================");
            System.out.println(contact.getId());
        }
    }

    @Test
    public void getAllContactUnSuccessTest() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization","sdgdfsgfghghg").build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);

        ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
        System.out.println(errorDto.getMessage());
        Assert.assertEquals(errorDto.getMessage(),"Wrong token format!");
    }
}
