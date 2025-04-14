$(document).ready(function () {
    loadHospitalIds();
    loadAmbulance();

});
var ambulanceID;

$("#save").click(function () {
    let ambulanceData = {
        vehicalNum: $("#vehicalNum").val(),
        driverName: $("#driverName").val(),
        driverContactNum: $("#driverContactNum").val(),
        hospitalAffiliation: $("#hospitalAffiliation").val(),
        hospitalID: $("#hospitalDropdown").val() // Assuming hospitalID is selected from a dropdown
    };

    // Send data via AJAX to the back-end to save the ambulance
    $.ajax({
        url: "http://localhost:8081/api/v1/ambulance/save",  // Replace with your actual API endpoint to save ambulance
        method: "POST",  // Use POST method to create a new ambulance
        contentType: "application/json",  // Send data as JSON
        data: JSON.stringify(ambulanceData),  // Convert the ambulance data to JSON string
        success: function (response) {
            alert(response.message);  // Success message

            loadAmbulance()
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

$("#update").click(function () {
    let updatedData = {
        ambulanceID: ambulanceID,  // Passing the ambulance ID to identify which one to update
        vehicalNum: $("#vehicalNum").val(),
        driverName: $("#driverName").val(),
        driverContactNum: $("#driverContactNum").val(),
        hospitalAffiliation: $("#hospitalAffiliation").val(),
        hospitalID: $("#hospitalDropdown").val()
    };

    $.ajax({
        url: `http://localhost:8081/api/v1/ambulance/update`,  // Endpoint for updating ambulance by ID
        method: "PUT",  // Use PUT method for updating existing resources
        contentType: "application/json",
        data: JSON.stringify(updatedData),
        success: function (response) {
            alert(response.message);  // Display success message
            loadAmbulance();  // Reload ambulance list after update
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

function loadAmbulance() {
    $.ajax({
        url: "http://localhost:8081/api/v1/ambulance/getAll",
        method: "GET",
        dataType: "json",
        success: function (response) {
            if (response.data) {
                let doctorTableBody = $("#ambulanceTableBody");

                doctorTableBody.empty(); // Clear existing table data



                response.data.forEach(ambu => {


                    let hos =   ambu.hospitalID ? ambu.hospitalID.hospitalID : "N/a";

                    let row = `<tr>
                        <td>${ambu.ambulanceID}</td>
                        <td>${ambu.driverContactNum}</td>
                        <td>${ambu.driverName}</td>
                        <td>${ambu.hospitalAffiliation}</td>
                        <td>${ambu.vehicalNum}</td>
                         <td>${hos}</td>
                        
                    </tr>`;
                    doctorTableBody.append(row);
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

    $("#ambulanceTableBody tr").click(function (){

        $("#ambulanceTableBody tr").removeClass("selected");

        $(this).addClass("selected");

        ambulanceID = $(this).find("td:eq(0)").text();
        $("#ambulanceID").text(ambulanceID);
        console.log(ambulanceID)
        let driverContactNum = $(this).find("td:eq(1)").text();
        let driverName = $(this).find("td:eq(2)").text();
        let hospitalAffiliation = $(this).find("td:eq(3)").text();
        let vehicalNum = $(this).find("td:eq(4)").text();
        let hospitalDropdown = $(this).find("td:eq(5)").text();

        $("#driverName").val(driverName)
        $("#driverContactNum").val(driverContactNum)
        $("#hospitalAffiliation").val(hospitalAffiliation)
        $("#vehicalNum").val(vehicalNum)
        $("#hospitalDropdown").val(hospitalDropdown)


    })

}

$("#deleteBtn").click(function () {
    if (!ambulanceID) {  // Check if an ambulance is selected
        alert("No ambulance selected to delete.");
        return;
    }

    if (confirm("Are you sure you want to delete this ambulance?")) {
        $.ajax({
            url: "http://localhost:8081/api/v1/ambulance/delete/" + ambulanceID,  // Endpoint for deleting an ambulance
            type: "DELETE",  // Use DELETE for removing an ambulance
            success: function (response) {
                alert("Ambulance deleted successfully!");
                console.log(response);
                loadAmbulance();  // Refresh the ambulance list after deletion
            },
            error: function (xhr, status, error) {
                alert("Error deleting ambulance: " + xhr.responseText);
                console.error(xhr, status, error);
            }
        });
    }
});



$("#clear").click(function () {
    function clearForm() {
        // Clear all form fields
        $("#ambulanceID").text("");
        $("#vehicalNum").val("");
        $("#driverName").val("");
        $("#driverContactNum").val("");
        $("#hospitalAffiliation").val("");
        $("#hospitalDropdown").val("");  // Reset hospital dropdown
        ambulanceID = null;  // Reset ambulanceID
    }
    clearForm();
});
