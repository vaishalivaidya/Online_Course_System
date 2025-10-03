const BASE = "http://localhost:8080";
let token = localStorage.getItem("jwt");

// Load approved courses
async function loadCourses() {
  const res = await fetch(BASE + "/student/courses", {
    headers: token ? { "Authorization": "Bearer " + token } : {}
  });
  const data = await res.json();
  const tbody = document.querySelector("#coursesTable tbody");
  tbody.innerHTML = "";
  data.forEach(c => {
    const row = `<tr>
      <td>${c.title}</td>
      <td>${c.fee}</td>
      <td>${c.category}</td>
      <td>${c.status}</td>
      <td><button onclick="enroll(${c.id})">Enroll</button></td>
    </tr>`;
    tbody.innerHTML += row;
  });
}

// Register user
async function registerUser(e) {
  e.preventDefault();
  const username = document.getElementById("regUsername").value;
  const password = document.getElementById("regPassword").value;
  const role = document.getElementById("regRole").value;
  const res = await fetch(BASE + "/auth/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password, role })
  });
  document.getElementById("message").innerText = await res.text();
}

// Login
async function login(e) {
  e.preventDefault();
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const res = await fetch(BASE + "/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password, role: "STUDENT" })
  });
  const data = await res.json();
  token = data.token;
  localStorage.setItem("jwt", token);
  document.getElementById("message").innerText = "Login successful!";
  setTimeout(() => window.location = "index.html", 1000);
}

// Enroll in course
async function enroll(courseId) {
  if (!token) { alert("Please login first"); return; }
  const res = await fetch(BASE + "/student/enroll/" + courseId, {
    method: "POST",
    headers: { "Authorization": "Bearer " + token }
  });
  alert("Enrollment response: " + await res.text());
}

// Load enrollments
async function loadEnrollments() {
  if (!token) { alert("Please login first"); return; }
  const res = await fetch(BASE + "/student/enrollments/me", {
    headers: { "Authorization": "Bearer " + token }
  });
  const data = await res.json();
  const tbody = document.querySelector("#enrollmentsTable tbody");
  tbody.innerHTML = "";
  data.forEach(e => {
    const row = `<tr>
      <td>${e.course.title}</td>
      <td>${e.progress}%</td>
      <td>${e.status}</td>
    </tr>`;
    tbody.innerHTML += row;
  });
}
