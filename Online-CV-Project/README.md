### Online CV Projesi

* Roller: Admin(insan kaynakları) user(kullanıcı)
* Database relation: admin(1) - user (N) ==> Spring Data(@OneToMany @ManyToOne ilişki üzerinde olmalıdır)
* Register/Login: Eğer kullanıcı üye değilse üye olması gerekiyor şifreler database maskelenmiş şekilde kaydedilmelidir. (Spring Security)
* Tanımlama:Müşteri şifresini girerek sisteme giriş yapar (Login için 3 giriş hakkı vardır yoksa bloke olur)
* İnsan kaynakları :Kullacılar adı,soyadı,telefon no,iş deneyimi,çalışma deneyimi v.s bilgilerini alacağı bir ekran olamlı
* İnsan kaynakları: Kullanıcı ekranında sisteme kayıt olan kişilerin bilgilerine erişim sağlamalı ve database filtreleme uygulama yapabilmeli örneğin: askerliği yapmış , 5 yıllık deneyime sahip kişiler ara dediğinde uygun kişiler gelmelidir.
* Kullanıcı sisteme bilgileri verirken: kullanıcı adı,soyadı, deneyim, CV ne zamana kadar sistemde saklanabilir (KVKK) v.b bilgiler olmalıdır.

* Loglama: Yapılan her bir işlem için mutlaka loglama tutmak gerekiyor.

