package com.longbeom.book.web;

import com.longbeom.book.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
     @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    /**
     *  1. param
     *  - API 테스트할 때 사용될 요청 파라미터를 설정
     *  - 단, 값은 String 만 허용됨
     *  - 그래서 숫자 / 날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능
     *
     *  2. jsonPath
     *  - JSON 응답값을 필드별로 검증할 수 있는 메소드
     *  - $를 기준으로 필드명을 명시
     *  - name 과 amount 를 검증하니 $.name, $.amount 로 검증
     */
    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
         String name = "hello";
         int amount = 1000;

         mvc.perform(
                 get("/hello/dto")
                         .param("name", name)
                         .param("amount", String.valueOf(amount)))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.name", is(name)))
                 .andExpect(jsonPath("$.amount", is(amount))
         );
    }
}
