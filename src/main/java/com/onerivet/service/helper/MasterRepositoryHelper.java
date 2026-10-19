package com.onerivet.service.helper;

import org.springframework.stereotype.Component;
import com.onerivet.repository.PlanRepository;
import com.onerivet.repository.StatusRepository;
import com.onerivet.repository.UserRepository;
import com.onerivet.repository.VehicleTypeRepository;

@Component
public class MasterRepositoryHelper {

	public final PlanRepository planRepository;
	public final StatusRepository statusRepository;
	public final UserRepository userRepository;
	public final VehicleTypeRepository vehicleTypeRepository;

	public MasterRepositoryHelper(PlanRepository planRepository, StatusRepository statusRepository,
			UserRepository userRepository, VehicleTypeRepository vehicleTypeRepository) {

		this.planRepository = planRepository;
		this.statusRepository = statusRepository;
		this.userRepository = userRepository;
		this.vehicleTypeRepository = vehicleTypeRepository;
	}
}