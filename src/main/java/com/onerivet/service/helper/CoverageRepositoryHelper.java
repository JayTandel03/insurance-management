package com.onerivet.service.helper;

import org.springframework.stereotype.Component;
import com.onerivet.repository.AddOnRepository;
import com.onerivet.repository.CoverageAddOnCatalogRepository;
import com.onerivet.repository.CoverageFieldRepository;
import com.onerivet.repository.CoverageRepository;
import com.onerivet.repository.CoverageTypeCatalogRepository;
import com.onerivet.repository.CoverageTypeRepository;
import com.onerivet.repository.VehicleTypeCoveragePriceRepository;

@Component
public class CoverageRepositoryHelper {

	public final CoverageRepository coverageRepository;
	public final CoverageTypeRepository coverageTypeRepository;
	public final CoverageFieldRepository coverageFieldRepository;
	public final CoverageTypeCatalogRepository coverageTypeCatalogRepository;
	public final CoverageAddOnCatalogRepository coverageAddOnCatalogRepository;
	public final VehicleTypeCoveragePriceRepository vehicleTypeCoveragePriceRepository;
	public final AddOnRepository addOnRepository;

	public CoverageRepositoryHelper(CoverageRepository coverageRepository,
	                                CoverageTypeRepository coverageTypeRepository, CoverageFieldRepository coverageFieldRepository,
	                                CoverageTypeCatalogRepository coverageTypeCatalogRepository,
	                                CoverageAddOnCatalogRepository coverageAddOnCatalogRepository,
	                                VehicleTypeCoveragePriceRepository vehicleTypeCoveragePriceRepository, AddOnRepository addOnRepository) {
		this.coverageRepository = coverageRepository;
		this.coverageTypeRepository = coverageTypeRepository;
		this.coverageFieldRepository = coverageFieldRepository;
		this.coverageTypeCatalogRepository = coverageTypeCatalogRepository;
		this.coverageAddOnCatalogRepository = coverageAddOnCatalogRepository;
		this.vehicleTypeCoveragePriceRepository = vehicleTypeCoveragePriceRepository;
		this.addOnRepository = addOnRepository;
	}
}