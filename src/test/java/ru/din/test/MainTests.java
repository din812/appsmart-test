package ru.din.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.din.test.controller.CustomerController;
import ru.din.test.dto.CustomerDto;
import ru.din.test.service.intefaces.CustomerService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class MainTests {

	@Mock
	CustomerService customerService;

	@Test
	void contextLoads() {
		CustomerController mock = Mockito.mock(CustomerController.class);
		ResponseEntity<CustomerDto> test = mock.createCustomer("test");
		//ResponseEntity<CustomerDto> test1 = mock.createCustomer("test"); <-- fail
		verify(mock, times(1)).createCustomer("test");
	}

	@Test
	 void test() {

	}
}
