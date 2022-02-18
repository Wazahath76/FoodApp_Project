package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Food;
import com.learning.dto.User;
//import com.learning.dto.Register;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.exception.TypeNotFoundException;
import com.learning.repository.FoodRepository;
import com.learning.repository.RoleRepository;
import com.learning.security.jwt.JwtUtils;
import com.learning.service.FoodService;
//import com.learning.service.UserService;
@RestController 
@RequestMapping("/api/food")
public class FoodController {
	@Autowired
	FoodService foodService;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/addFood")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) throws AlreadyExistsException {
	
			Food result =foodService.addFood(food);
			System.out.println(result);
			return ResponseEntity.status(201).body(result);
			
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") int id) throws IdNotFoundException{
		Food food=foodService.getFoodById(id);
		return ResponseEntity.ok(food);
	}
	
	@GetMapping("/type/{foodType}")
	public ResponseEntity<?> getFoodByFoodType(@PathVariable("foodType") String foodType) throws TypeNotFoundException{
		Optional<List<Food>> food=foodService.getFoodByfoodType(foodType);
		return ResponseEntity.ok(food);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllFoodDetails() {
		Optional <List<Food>> optional =foodService.getAllFoodDetails();
		
		if(optional.isEmpty()) {
			Map<String,String> map = new HashMap<>();
			map.put("message", "no record found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
			
		}
		return ResponseEntity.ok(optional.get());
		

	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFood(@PathVariable("id") int id,@RequestBody Food food) throws IdNotFoundException{
		Food result = foodService.updateFood(id, food);
		return ResponseEntity.status(201).body(result);
	}
	
	@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> DeleteUserById(@PathVariable("id") int id) throws IdNotFoundException{
		String result = foodService.deleteFoodItemById(id);
		System.out.println(result);
		Map<String, String> map = new HashMap<>();
		map.put("message", "User deleted successfully");
		return ResponseEntity.status(201).body(result);
	}
	
	
}