package org.mcsg.plotmaster.managers

import org.mcsg.plotmaster.Region

class RegionCreation {

	enum RegionCreationStatus {
		SUCCESS("Region Created Successfully."),
		REGION_EXISTS("A region already exists in this location!"),
		OTHER("Failed to create region.")

		String message;

		public CreationStatus(String message){
			this.message = message;
		}


	}
	
	
	RegionCreationStatus status
	Region region
	
}
