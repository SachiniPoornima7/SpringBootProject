// Call load function on page load
$(document).ready(function () {
    loadHospitalIds();
    loadDoctor();
    LoadDataIntoInput();
});


var docId;
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

$("#saveHos").click(function () {
    let doctorData = {

        name: $("#doctorName").val(),
        email: $("#email").val(),
        contactNum: $("#contact").val(),
        hospitalID: $("#hospitalDropdown").val(),
        gender:$("#Gender").val()
    };

    $.ajax({
        url: "http://localhost:8081/api/v1/doctor/save",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(doctorData),
        success: function (response) {
            alert("Doctor saved successfully!");
            console.log(response);
            loadDoctor();
             // Refresh doctor list
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


function loadDoctor() {
    $.ajax({
        url: "http://localhost:8081/api/v1/doctor/getAll",
        method: "GET",
        dataType: "json",
        success: function (response) {
            if (response.data) {
                let doctorTableBody = $("#doctorTableBody");

                doctorTableBody.empty(); // Clear existing table data



                response.data.forEach(doctor => {


                  let hos =   doctor.hospitalID ? doctor.hospitalID.hospitalID : "N/a";

                    let row = `<tr>
                        <td>${doctor.doctorID}</td>
                        <td>${doctor.name}</td>
                        <td>${doctor.gender}</td>
                        <td>${doctor.email}</td>
                        <td>${doctor.contactNum}</td>
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

    $("#doctorTableBody tr").click(function (){

        $("#doctorTableBody tr").removeClass("selected");

        $(this).addClass("selected");

        docId = $(this).find("td:eq(0)").text();
        $("#docID").text(docId);
        console.log(docId)
        let doctorName = $(this).find("td:eq(1)").text();
        let Gender = $(this).find("td:eq(2)").text();
        let email = $(this).find("td:eq(3)").text();
        let contact = $(this).find("td:eq(4)").text();
        let hospitalDropdown = $(this).find("td:eq(5)").text();

       $("#doctorName").val(doctorName)
          $("#email").val(email)
            $("#contact").val(contact)
          $("#hospitalDropdown").val(hospitalDropdown)
         $("#Gender").val(Gender)


    })

}


$("#update").click(function () {
    let updatedDoctorData = {
        doctorId: docId,  // The ID of the doctor to be updated
        name: $("#doctorName").val(),
        email: $("#email").val(),
        contactNum: $("#contact").val(),
        hospitalID: $("#hospitalDropdown").val(),
        gender: $("#Gender").val()
    };

    $.ajax({
        url: "http://localhost:8081/api/v1/doctor/update",  // Ensure the URL is correct for updating doctor data
        type: "PUT",  // Using PUT for update operation
        contentType: "application/json",
        data: JSON.stringify(updatedDoctorData),
        success: function (response) {
            alert("Doctor updated successfully!");
            console.log(response);
            loadDoctor();  // Refresh the doctor list
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
    if (!docId) {
        alert("No doctor selected to delete.");
        return;
    }

    if (confirm("Are you sure you want to delete this doctor?")) {
        $.ajax({
            url: "http://localhost:8081/api/v1/doctor/delete/" + docId,  // Ensure the URL is correct for deleting a doctor
            type: "DELETE",  // Using DELETE for delete operation
            success: function (response) {
                alert("Doctor deleted successfully!");
                console.log(response);
                loadDoctor();  // Refresh the doctor list
            },
            error: function (xhr, status, error) {
                alert("Error deleting doctor: " + xhr.responseText);
                console.error(xhr, status, error);
            }
        });
    }
});



    // Clear input fields in the form


// Remove selection from the table (
    $("#clear").click(function (){

        $("#doctorName").val("");
        $("#email").val("");
        $("#contact").val("");
        $("#hospitalDropdown").val(""); // Optionally reset the dropdown
        $("#Gender").val(""); // Optionally reset gender field
        $("#docID").text(""); // Optionally reset the doctor ID text (if used for display)


    });