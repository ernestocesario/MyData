# MyData

**MyData** is a file management software (developed for the **User Inferface Design** course assignment at the university), written in **Java** that uses the **JavaFX framework** for the GUI.  

**MyData** allows you to add existing files on your pc to **protect** them or to **share** them over the **local network**.  
You can also **open files** to view or edit them, **delete** them permanently, or **remove** them from the management software.  
<br>

## File protection
Files are protected using **AES256**.  
The **key** used for **AES256** is generated from the **user's password** hashed with the **PBKDF2WithHmacSHA256** algorithm.  
In turn, the user **password** is stored in the **application database** using the **BCrypt** hashing algorithm with **2^15 iterations**.  
<br>

## File sharing
As for **sharing files**, this is possible in the local network.  
Particularly when files are to be sent, it is possible to choose a **network interface** so as to handle communication over several local networks.  
<br>

## Additional features
**Additional features** that can be changed from the app settings are:  
- change password  
- delete accounts  
- change UI style and theme  
- change units of measurement for displaying file sizes  
- use fonts suitable for people with dyslexia  
- ability to trigger an alert before removing or deleting files  
- lock files when closing the app  
- automatically unlock files you want to open via app  
- automatically unlock files you want to send  
<br>

## Some screenshots

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/login.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/home_with_elements.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/home.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/send_files.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/recv_files.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/choose_network_interface.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/account_settings.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/ui_settings.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/app_settings.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/accessibility_settings.png" />
</p>
<br>

---
<br>

<p align="center">
  <img src="https://github.com/ernestocesario/MyData/blob/main/Screenshots/about_window.png" />
</p>