package com.aak1247.controllers;


import com.aak1247.Application;
import com.aak1247.models.Passport;
import com.aak1247.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author DDHEE on 2017/3/14.
 */

/**
 * 说明：
 * 通过MockMvc发出请求
 * 通过sessisonAtrs设定session
 * 通过content设定请求体
 * 通过andExpect进行响应的断言测试
 */

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@SpringBootTest(classes = Application.class)
@SpringBootConfiguration
public class UserTest {
    private String usernameValid = "aak1247";
    private String usernameValid1 = "aak1248";
    private String usernameDuplicated = "  z ccz 1  3 ";
    private String usernameEmpty = "      ";
    private String passwordValid = "abc6789067890";
    private String passwordWrong = "ab6789067890";
    private String passwordEmpty = "";

    @Autowired
    private UserRepository userRepository;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private HashMap<String, Object> sessionAttr;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private Passport testUserPassport = new Passport();

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void validUser() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();//加载上下文
        testUserPassport.username = usernameValid;
        testUserPassport.password = passwordValid;

        //session
        //维持登录态
        sessionAttr = new HashMap<String, Object>();
    }

//    @Test
//    public void signUpTestNormal() throws Exception {
//        mockMvc.perform(post("/rest/user/signup")
//                .content(this.json(testUserPassport))
//                .contentType(contentType))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn();
//    }

    @Test
    public void signInTestNormal() throws Exception {
        mockMvc.perform(post("/rest/user/signup")
                .content(this.json(testUserPassport))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        mockMvc.perform(post("/rest/user/signin")
                .content(this.json(testUserPassport))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @After
    public void clear() {//去除加入的数据
        userRepository.deleteAll();
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
