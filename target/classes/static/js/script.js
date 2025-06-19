// Theme management functions
function getTheme() {
  // Check localStorage first, then system preference, default to light
  const storedTheme = localStorage.getItem("theme");
  if (storedTheme) return storedTheme;
  
  return window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
}

function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function applyTheme(theme) {
  const html = document.documentElement;
  if (theme === "dark") {
    html.classList.add("dark");
  } else {
    html.classList.remove("dark");
  }
  
  // Update button text
  const themeButton = document.getElementById("themeToggle");
  if (themeButton) {
    themeButton.querySelector("span").textContent = theme === "dark" ? "Light" : "Dark";
    // Also update icon
    const icon = themeButton.querySelector("i");
    icon.className = theme === "dark" ? "fa-regular fa-sun" : "fa-regular fa-moon";
  }
}

// Toggle theme function
function changeTheme() {
  const currentTheme = getTheme();
  const newTheme = currentTheme === "dark" ? "light" : "dark";
  setTheme(newTheme);
  applyTheme(newTheme);
}

// Initialize theme on page load
document.addEventListener("DOMContentLoaded", () => {
  const currentTheme = getTheme();
  applyTheme(currentTheme);
});


// signup form passsword section 
const form = document.getElementById('signupForm');

 form.addEventListener('submit', function (e) {
  const password = document.getElementById('password').value.trim();
  const confirmPassword = document.getElementById('confirm_password').value.trim();

  if (password !== confirmPassword) {
    e.preventDefault(); // ⛔️ Only stop if invalid
    alert('❌ Passwords do not match! Should be same');
    return;
  }

  // ✅ Let form submit normally if valid
});
