### İnsan Kaynakları Projesi:

* Roller: Admin(insan kaynakları) user(çalışanlar)
* Database relation: admin(1) - user (N) ==> Spring Data(@OneToMany @ManyToOne ilişki üzerinde olmalıdır)
* Register/Login: Eğer kullanıcı üye değilse üye olması gerekiyor şifreler database maskelenmiş şekilde kaydedilmelidir. (Spring Security)
* Tanımlama: Çalışanlar şifresini girerek sisteme giriş yapar. (Login için 3 giriş hakkı vardır yoksa bloke olur)
* İnsan kaynakları her bir çalışan bilgisine erişebilmelidir.
* Hangi çalışana ne kadar maaş veriliyor bilgisine v.b erişim sağlamalıdır.
* Hangi çalışanının hangi gün izin verildiği bilgisine erişim sağlaması gerekiyor.
* Loglama: Yapılan her bir işlem için mutlaka loglama tutmak gerekiyor

