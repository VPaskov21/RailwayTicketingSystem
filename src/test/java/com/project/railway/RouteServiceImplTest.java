package com.project.railway;

import com.project.railway.data.entity.RouteList;
import com.project.railway.data.entity.Station;
import com.project.railway.data.repository.RouteRepository;
import com.project.railway.data.repository.StationRepository;
import com.project.railway.service.RouteService;
import java.util.List;


import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RouteServiceImplTest {

    @Autowired
    private RouteService routeService;

    @Test
    public void shouldReturnRoutesBetweenTwoStations(){
        final List<RouteList> routeList = routeService.findRoutesBetweenStations("Sofia",
                                                                                    "Plovdiv",
                java.time.LocalDate.parse(java.time.LocalDate.now().toString()).plusDays(1).toString());

        final List<RouteList> blankRouteList = new ArrayList<>();

        assertThat(routeList).isNotNull();
        assertThat(routeList).isNotEqualTo(blankRouteList);
    }

    @Test
    public void shouldNotReturnResults(){
        final List<RouteList> routeList = routeService.findRoutesBetweenStations("Test", "Test", java.time.LocalDate.now().toString());

        final List<RouteList> blankRouteList = new ArrayList<>();

        assertEquals(routeList, blankRouteList);
    }
}
