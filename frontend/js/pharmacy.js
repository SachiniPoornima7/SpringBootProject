$(document).ready(function () {
    loadHospitalIds();
    loadPharmacy();


});
var pharmacyID;


function loadHospitalIds() {
    $.ajax({
        url: "http://localhost:8081/api/v1/hospital/getAllIds",
        method: "GET",
        dataType: "json",
        success: function (response) {
            if (!response.data || response.data.length === 0) {
                alert("No hospital IDs found.");
                return;
            } else {
                alert("Hospitals found.");
            }

            // Log the response to check the data structure
            let dropdown = $("#hospitalDropdown");
            dropdown.empty();
            dropdown.append(`<option value="">Select a hospital ID</option>`);

            // Iterate over each object in the response.data array
            response.data.forEach(hos => {
                let hosid = hos.hospitalID;  // Access hospital ID
                let hospitalName = hos.hospitalName;  // Access hospital Name

                // Create the dropdown option with both ID and Name
                let option = `<option value="${hosid}">${hosid}</option>`;
                dropdown.append(option);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error fetching hospitals:", xhr, status, error);
            alert("Failed to load hospital IDs.");
        }
    });
}


$("#save").click(function () {
    let ambulanceData = {
        name: $("#pharmacyname").val(),
        address: $("#pharmacyaddress").val(),
        contactNum: $("#pharmacycontact").val(),
        email: $("#pharmacyemail").val(),
        hospitalID: $("#hospitalDropdown").val() // Assuming hospitalID is selected from a dropdown
    };

    $.ajax({
        url: "http://localhost:8081/api/v1/pharmacy/save",  // Adjust URL for ambulance save
        method: "POST",  // POST method for creating a new ambulance
        contentType: "application/json",  // Send data as JSON
        data: JSON.stringify(ambulanceData),  // Convert ambulance data to JSON
        success: function (response) {
            alert(response.message);  // Success message

            loadPharmacy()
        },
        error: function (error) {
            let errorMessage = "An error occurred. Please try again.";

            // Check if the error response contains validation errors
            if (error.responseJSON && error.responseJSON.data) {
                // Extract the first error message from the data object
                const errorData = error.responseJSON.data;
                const firstErrorKey = Object.keys(errorData)[0]; // Get the first key (e.g., "hospitalName")
                errorMessage = errorData[firstErrorKey]; // Get the error message for that key
            }

            // Display the error message using SweetAlert
            alert(errorMessage)
        }
    });
});


function loadPharmacy() {
    $.ajax({
        url: "http://localhost:8081/api/v1/pharmacy/getAll",
        method: "GET",
        dataType: "json",
        success: function (response) {
            if (response.data) {
                let pharmacyTableBody = $("#pharmacyTableBody");

                pharmacyTableBody.empty(); // Clear existing table data



                response.data.forEach(pharmacy => {


                    let hos =   pharmacy.hospital ? pharmacy.hospital.hospitalID : "N/a";

                    let row = `<tr>
                        <td>${pharmacy.pharmacyID}</td>
                        <td>${pharmacy.name}</td>
                        <td>${pharmacy.address}</td>
                        <td>${pharmacy.contactNum}</td>
                        <td>${pharmacy.email}</td>
                         <td>${hos}</td>
                        
                    </tr>`;
                    pharmacyTableBody.append(row);
                });
                LoadDataIntoInput();


                // Attach event handlers for edit/delete
            }
        },
        error: function (xhr, status, error) {
            console.error("Error loading doctors:", xhr, status, error);
            alert("Failed to load doctor data.");
        }
    });
}


function LoadDataIntoInput(){

    $("#pharmacyTableBody tr").click(function (){

        $("#pharmacyTableBody tr").removeClass("selected");

        $(this).addClass("selected");

        pharmacyID = $(this).find("td:eq(0)").text();
        $("#pharmacyId").text(pharmacyID);
        console.log(pharmacyID)
        let Name = $(this).find("td:eq(1)").text();
        let address = $(this).find("td:eq(2)").text();
        let contact = $(this).find("td:eq(3)").text();
        let email = $(this).find("td:eq(4)").text();
        let hospitalDropdown = $(this).find("td:eq(5)").text();

             $("#pharmacyname").val(Name)
             $("#pharmacyaddress").val(address)
             $("#pharmacycontact").val(contact)
             $("#pharmacyemail").val(email)
             $("#hospitalDropdown").val(hospitalDropdown)

    })

}

$("#clear").click(function () {
    // Clear all the input fields and dropdown selection
    $("#pharmacyId").text("");  // Clear the pharmacy ID text
    $("#pharmacyname").val("");  // Clear the pharmacy name input
    $("#pharmacyaddress").val("");  // Clear the pharmacy address input
    $("#pharmacycontact").val("");  // Clear the pharmacy contact input
    $("#pharmacyemail").val("");  // Clear the pharmacy email input
    $("#hospitalDropdown").val("");  // Clear the hospital dropdown selection
    // Optionally, remove any selected row styling (if applicable)
    $("#pharmacyTableBody tr").removeClass("selected");
});

$("#update").click(function () {
    // Get the pharmacy ID from the hidden or text element (ensure it's set)
    let pharmacyID = $("#pharmacyId").text().trim();

    if (!pharmacyID) {
        alert("Please select a pharmacy to update.");
        return;
    }

    // Prepare the updated pharmacy data from the form fields
    let updatedPharmacyData = {
        pharmacyID: pharmacyID,
        name: $("#pharmacyname").val(),
        address: $("#pharmacyaddress").val(),
        contactNum: $("#pharmacycontact").val(),
        email: $("#pharmacyemail").val(),
        hospitalID: $("#hospitalDropdown").val() // Selected hospital ID from dropdown
    };

    // Make the AJAX request to update the pharmacy
    $.ajax({
        url: "http://localhost:8081/api/v1/pharmacy/update",  // Replace with the correct update API endpoint
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedPharmacyData),  // Convert updated pharmacy data to JSON
        success: function (response) {
            alert(response.message);  // Show success message
            loadPharmacy();  // Reload pharmacy data after update
        },
        error: function (error) {
            let errorMessage = "An error occurred. Please try again.";

            // Check if the error response contains validation errors
            if (error.responseJSON && error.responseJSON.data) {
                // Extract the first error message from the data object
                const errorData = error.responseJSON.data;
                const firstErrorKey = Object.keys(errorData)[0]; // Get the first key (e.g., "hospitalName")
                errorMessage = errorData[firstErrorKey]; // Get the error message for that key
            }

            // Display the error message using SweetAlert
            alert(errorMessage)
        }
    });
});

$("#deleteBtn").click(function () {
    // Get the pharmacy ID from the hidden or text element (ensure it's set)
    let pharmacyID = $("#pharmacyId").text().trim();

    if (!pharmacyID) {
        alert("Please select a pharmacy to delete.");
        return;
    }

    // Make the AJAX request to delete the pharmacy
    $.ajax({
        url: `http://localhost:8081/api/v1/pharmacy/delete/${pharmacyID}`,  // Replace with the correct delete API endpoint
        method: "DELETE",
        contentType: "application/json",
        success: function (response) {
            alert(response.message);  // Show success message
            loadPharmacy();  // Reload pharmacy data after deletion
        },
        error: function (error) {
            console.error("Error deleting pharmacy:", error);  // Log the error
            alert("Error deleting pharmacy: " + error.responseText);  // Show error message
        }
    });
});
