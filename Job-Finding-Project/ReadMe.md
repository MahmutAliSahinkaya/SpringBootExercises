### İş Bulma Projesi

* Roller: Admin(şirket) user(kullanıcı)
* Database relation: admin(1) - user (N) ==> Spring Data(@OneToMany @ManyToOne ilişki üzerinde olmalıdır)
* Register/Login: Eğer kullanıcı üye değilse üye olması gerekiyor şifreler database maskelenmiş şekilde kaydedilmelidir. (Spring Security)
* Tanımlama:kullanıcı şifresini girerek sisteme giriş yapar (Login için 3 giriş hakkı vardır yoksa bloke olur)
* Kullacılar adı,soyadı,telefon no,iş deneyimi,çalışma deneyimi v.s bilgilerini alacağı bir ekran olmalı.
* Şirket: Şirket ekranında sisteme kayıt olan kişilerin bilgilerine erişim sağlamalı ve database filtreleme uygulama yapabilmeli örneğin: askerliği yapmış , 5 yıllık deneyime sahip kişiler.
* Başka bir sayfada aranılan özellikler belirtilmeli örneğin: X kişi iş arıyor bu ekrana baktığında bana uygun iş var mı ? Kullanıda bu ekranda filtreleme yapabilmelidir.
* Loglama: Yapılan her bir işlem için mutlaka loglama tutmak gerekiyor

