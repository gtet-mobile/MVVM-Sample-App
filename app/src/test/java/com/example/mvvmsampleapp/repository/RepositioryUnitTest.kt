import com.example.mvvmsampleapp.model.Data
import com.example.mvvmsampleapp.model.Support
import com.example.mvvmsampleapp.model.UserDetailResponse
import com.example.mvvmsampleapp.model.UserResponse
import com.example.mvvmsampleapp.repository.UserRepository
import com.example.mvvmsampleapp.retrofit.ApiHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class UserRepositoryTest {

    @Mock
    lateinit var apiHelper: ApiHelper

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepository(apiHelper)
    }

    @Test
    fun testUsersForEmptyUsersNotAvailable() = runBlocking {
        // Mock response
        val mockResponse = Response.success(UserResponse(emptyList(), 10, 6, support = Support(text = "", url = ""), 12, 2))
//        val mockResponse2 = Response.success(UserResponse(data = listOf(Data(avatar = "", email = "", firstName = "", id = 1, lastName = "")) , 1, 6, support = Support(text = "", url = ""), 12, 2))

        // Mock APIHelper method
        `when`(apiHelper.getUsers(10)).thenReturn(mockResponse)
//        `when`(apiHelper.getUsers(2)).thenReturn(mockResponse2)

        // Call the function being tested
        val result = userRepository.getUsers(10)

        // Verify the result
        Assert.assertEquals(mockResponse, result);
//        Assert.assertEquals(mockResponse2, result);

        Assert.assertEquals(result?.body()?.data?.isEmpty(), true)
    }

    @Test
    fun testUsersWhenUsersAvailable() = runBlocking {
        // Mock response
       val mockResponse = Response.success(UserResponse(data = listOf(Data(avatar = "", email = "", firstName = "", id = 1, lastName = "")) , 1, 6, support = Support(text = "", url = ""), 12, 2))

        // Mock APIHelper method
        `when`(apiHelper.getUsers(1)).thenReturn(mockResponse)
//        `when`(apiHelper.getUsers(2)).thenReturn(mockResponse2)

        // Call the function being tested
        val result = userRepository.getUsers(1)

        // Verify the result
        Assert.assertEquals(mockResponse, result);
//        Assert.assertEquals(mockResponse2, result);

        Assert.assertEquals(result?.body()?.data?.size, 1)
    }

    @Test
    fun testGetUsersDetails() = runBlocking {
        // Mock response
        val mockResponse = Response.success(UserDetailResponse(data = Data(avatar = "", email = "hello@test.com", firstName = "hello", id = 1, lastName = ""), support = Support(text = "", url = "")))

        // Mock APIHelper method
        `when`(apiHelper.getUsersDetails(1)).thenReturn(mockResponse)

        // Call the function being tested
        val result = userRepository.getUsersDetails(1)

        // Verify the result
        Assert.assertEquals(mockResponse, result);

        Assert.assertEquals(mockResponse?.body()?.data?.email, "hello@test.com");
        Assert.assertEquals(mockResponse?.body()?.data?.id, 1);
        Assert.assertEquals(mockResponse?.body()?.data?.firstName, "hello");

    }
}
