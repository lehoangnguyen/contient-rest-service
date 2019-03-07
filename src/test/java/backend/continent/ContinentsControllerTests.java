/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package backend.continent;

import backend.continent.entity.Continent;
import backend.continent.entity.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContinentsControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllContinents() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/continents")).andDo(print()).andExpect(status().isOk())
                .andReturn();
        List<Continent> continentList = mapper.readValue(result.getResponse().getContentAsString(),  new TypeReference<List<Continent>>(){});
        Assert.assertEquals(5, continentList.size());
    }

    @Test
    public void testQueryByContinentName() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/continents/Africa")).andDo(print()).andExpect(status().isOk())
                .andReturn();
        List<Country> countryList = mapper.readValue(result.getResponse().getContentAsString(),  new TypeReference<List<Country>>(){});
        Assert.assertEquals(5, countryList.size());
        Assert.assertEquals(1, countryList.stream().filter(c->c.getName().equals("Nigeria")).count());
    }

    @Test
    public void testQueryByContinentName_notFound() throws Exception {
        this.mockMvc.perform(get("/continents/ABC")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testQueryByCountryName() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/country/Australia")).andDo(print()).andExpect(status().isOk())
                .andReturn();
        Country country = mapper.readValue(result.getResponse().getContentAsString(),  Country.class);
        Assert.assertNotNull(country);
        Assert.assertEquals("Australia", country.getName());
        Assert.assertNotNull(country.getFlag());

    }

    @Test
    public void testQueryByCountryName_notFound() throws Exception {
        this.mockMvc.perform(get("/country/ABC")).andDo(print()).andExpect(status().isNotFound());
    }

}
