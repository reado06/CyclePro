# 🚲 CyclePro - Penjualan Sepeda 🚲

---

## 📋 Deskripsi Project

**CyclePro** adalah aplikasi manajemen penjualan sepeda berbasis Java yang dibangun menggunakan GUI Swing. Aplikasi ini dirancang untuk memudahkan toko sepeda dalam melakukan operasi sehari-hari, dari pengelolaan inventaris hingga transaksi penjualan.

---

## 🛠️ Teknologi yang Digunakan

-   **Java**: Bahasa pemrograman utama yang digunakan.
-   **Java Swing & AWT**: Library yang digunakan untuk membangun antarmuka pengguna (GUI) yang interaktif dan kaya fitur.
-   **SQLite**: Database relasional yang digunakan untuk menyimpan data pengguna, produk, dan pesanan secara lokal.
-   **JDBC (Java Database Connectivity)**: API yang menjembatani aplikasi Java dengan database SQLite.
-   **Object-Oriented Programming (OOP)**: Paradigma pemrograman yang mendasari struktur proyek.

---

## ✨ Fitur Utama

### 1. Sistem Autentikasi Ganda (User & Admin)
-   **Halaman Login**: Memvalidasi kredensial pengguna dan dapat membedakan antara login untuk `User` biasa dan `Admin`.
-   **Halaman Registrasi**: Pengguna baru dapat membuat akun dengan mengisi username, password, alamat, dan nomor telepon.
-   **Akses Berbasis Peran**: Aplikasi mengarahkan pengguna ke dasboard yang berbeda (User Dashboard atau Admin Dashboard) tergantung pada peran akun.

### 2. Dashboard Pengguna
-   **Navigasi Kategori**: Tampilan utama yang memungkinkan pengguna memilih kategori sepeda yang ingin dilihat (BMX, Gunung, Lipat).
-   **Akses Riwayat Pesanan**: Terdapat tombol untuk langsung menuju halaman riwayat pesanan pengguna.
-   **Personalisasi**: Menampilkan sapaan "Halo, [username]!" untuk pengguna yang sedang login.

### 3. Dashboard Admin & Manajemen Pesanan
-   **Tampilan Semua Pesanan**: Admin dapat melihat seluruh pesanan yang masuk dari semua pengguna dalam format tabel yang detail.
-   **Ubah Status Pesanan**: Admin memiliki kewenangan untuk mengubah status pesanan, misalnya dari "Diproses" menjadi "Dikirim" atau "Selesai".
-   **Hapus Pesanan**: Admin dapat menghapus data pesanan dari sistem jika diperlukan.

### 4. Katalog Produk dan Alur Pembayaran
-   **Daftar Produk per Kategori**: Menampilkan produk sepeda lengkap dengan gambar, nama, harga, dan sisa stok.
-   **Halaman Pembayaran**: Halaman checkout di mana pengguna dapat memilih metode pembayaran (Virtual Account atau COD) dan kurir pengiriman.
-   **Cetak Struk Digital**: Setelah pembayaran, sistem akan menampilkan struk konfirmasi pesanan yang berisi detail transaksi.

### 5. Desain Antarmuka Pengguna (UI)
-   **Tema Warna Konsisten**: Menggunakan class `Colors.java` untuk memastikan konsistensi palet warna di seluruh jendela aplikasi, memudahkan penggunaan warna di setiap class serta memberikan tampilan yang profesional.
-   **Komponen Kustom**: Menggunakan komponen Swing kustom seperti `RoundedTextField` dan `RoundedJPasswordField` untuk input field yang lebih modern.
-   **Efek Interaktif**: Adanya efek visual seperti perubahan warna dan border saat mouse *hover* di atas tombol atau kartu produk untuk meningkatkan pengalaman pengguna (UX).

---
