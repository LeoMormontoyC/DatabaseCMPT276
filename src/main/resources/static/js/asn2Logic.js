document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".removeBtn").forEach((button) => {
    button.addEventListener("click", function () {
      const studentId = this.getAttribute("data-student-id");
      const form = document.createElement("form");
      form.method = "post";
      form.action = "/students/remove/" + studentId;
      document.body.appendChild(form);
      form.submit();
    });
  });

  //   document.querySelectorAll(".editBtn").forEach((button) => {
  //     button.addEventListener("click", function () {
  //       const studentId = this.getAttribute("data-student-id");
  //       const form = document.createElement("form");
  //       form.method = "get";
  //       form.action = "/students/edit/" + studentId;
  //       document.body.appendChild(form);
  //       form.submit();
  //     });
  //   });
});
