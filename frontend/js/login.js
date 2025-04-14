$("#btnlogin").click(function () {
    console.log("hii");

    let username = $("#UserName").val();
    let pass = $("#password").val();

    let data = {
        userName: username,
        password: pass
    };

    $.ajax({
        url: "http://localhost:8081/auth/login",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",  // Expect a JSON response
        success: function (res) {
            console.log("Response:", res);  // Log the response

            // Check if the response contains a success message
            if (res.message === "Login successful") {
                console.log("Redirecting to dashboard");
                window.location.replace("../template/index1.html?_=" + new Date().getTime());
            } else {
                alert("Invalid credentials");
            }
        },
        error: function (err) {
            console.log("Error:", err);
            console.log("Error status: ", err.status);  // Log status code
            alert("Invalid credentials");
        }
    });
});
