$(document).ready(function () {

});
var HospitalId;


$("#saveHos").click(function () {
    let hospitalData = {
        hospitalID: $("#hospitalId").text().trim(),  // Getting ID from h3 tag
        hospitalName: $("#hospitalName").val(),
        address: $("#hospitalAddress").val(),
        contactNum: $("#hospitalContact").val(),
        email: $("#hospitalEmail").val()
    };

    $.ajax({
        url: "http://localhost:8081/api/v1/hospital/save", // Adjust URL as needed
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(hospitalData),
        success: function (response) {
            alert("Hospital saved successfully!");
            console.log(response);
            load();
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




function load() {
    $.ajax({
        url: "http://localhost:8081/api/v1/hospital/getAll", // Adjust the URL if necessary
        method: "GET",
        dataType: "json",
        success: function (response) {
            if (response.data) {
                let HospitalTableBody = $("#HospitalTableBody");

                HospitalTableBody.empty(); // Clear existing table data

                response.data.forEach(hospital => {
                    let row = `<tr>
                        <td>${hospital.hospitalID}</td>
                        <td>${hospital.hospitalName}</td>
                        <td>${hospital.address}</td>
                        <td>${hospital.contactNum}</td>
                        <td>${hospital.email}</td>
                        
                    </tr>`;
                    HospitalTableBody.append(row);
                });

                attachEventHandlers();
                LoadDataIntoInput();// Attach event handlers for edit/delete
            }
        },
        error: function (xhr, status, error) {
            console.error("Error loading hospitals:", xhr, status, error);
            alert("Failed to load hospital data.");
        }
    });
}

// Function to attach click events to dynamically added elements
function attachEventHandlers() {
    $(".edit-btn").click(function () {
        let hospitalID = $(this).data("id");
        editHospital(hospitalID);
    });

    $(".delete-btn").click(function () {
        let hospitalID = $(this).data("id");
        deleteHospital(hospitalID);
    });
}

// Call load function on page load
$(document).ready(function () {
    load();
});



$("#deleteBtn").click(function () {
    let  id = $("#hospitalId").text().trim();


    $.ajax({
        url: `http://localhost:8081/api/v1/hospital/delete/${id}`, // Adjust URL as needed
        type: "DELETE",
        contentType: "application/json",

        success: function (response) {
            alert(response.message);
            console.log(response);
            load();
        },
        error: function (xhr, status, error) {
            alert("Error saving hospital: " + xhr.responseText);
            console.error(xhr, status, error);
        }
    });
});


function LoadDataIntoInput(){

    $("#HospitalTableBody tr").click(function (){

        $("#HospitalTableBody tr").removeClass("selected");

        $(this).addClass("selected");

        HospitalId = $(this).find("td:eq(0)").text();
        $("#hospitalId").text(HospitalId);
        console.log(HospitalId)
        let HospitalName = $(this).find("td:eq(1)").text();
        let HospitalAddress = $(this).find("td:eq(2)").text();
        let contact = $(this).find("td:eq(3)").text();
        let email = $(this).find("td:eq(4)").text();

        $("#hospitalName").val(HospitalName);
        $("#hospitalAddress").val(HospitalAddress);
        $("#hospitalContact").val(contact);
        $("#hospitalEmail").val(email);


    })

}


$("#clear").click(function (){
    $("#hospitalId").text(" ");
    $("#hospitalName").val("");
    $("#hospitalAddress").val("");
    $("#hospitalContact").val("");
    $("#hospitalEmail").val("");
})


$("#update").click(function () {
    let hospitalID = $("#hospitalId").text().trim(); // Ensure hospital ID is set

    if (!hospitalID) {
        alert("Please select a hospital to update.");
        return;
    }

    let updatedHospitalData = {
        hospitalID: hospitalID, // Getting ID from h3 tag
        hospitalName: $("#hospitalName").val(),
        address: $("#hospitalAddress").val(),
        contactNum: $("#hospitalContact").val(),
        email: $("#hospitalEmail").val()
    };

    $.ajax({
        url: "http://localhost:8081/api/v1/hospital/update", // Adjust URL as needed
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedHospitalData),
        success: function (response) {
            alert("Hospital updated successfully!");
            console.log(response);
            load(); // Refresh table after update
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
