# Zeplin-Problemi

Bu proje Java ile geliştirilmiştir. Bu projede zeplin ile Türkiye de bir şehirden başka bir şehire gitmek için en kısa yolu bulmak amaçlanmıştır. Bu projede ayrıca En Kısa Yol problemini çözmek de amaçlanmıştır. Bu problem Dijkstra algoritması kullanılarak çözülmüştür. 

Zeplinin hareketini belirleyen bazı etkenler vardır. Zeplinin içerisindeki yolcu sayısına göre yapabileceği max eğim artmaktadır veya azalmaktadır. Şehirler arası eğim ise şehirlerin sahip olduğu longitude, latitude ve rakım değerleri ile eğim hesaplanarak zeplin hareketi için güzergâh belirlenir. Sonrasında bu güzergahlar arasında en kısa yol bulunarak hareket etme işlemi gerçekleştirilir. 

Ayrıca zeplinin belirlenen güzergahta en karlı bir şekilde hareket etmesi için alması gereken yolcu sayısıda program tarafından bulunmaktadır. Çünkü yolcu sayısı arttıkça zeplinin yapabileceği max eğim azalmaktadır. Bu da komşu şehirler arası eğim hesaba katıldığında güzergah değişebilir ve gidilen yol mesafesi artabilir. Mesafe arttıkça yapılan yakıt masrafıda artacağı icin ne kadar çok yolcu o kadar kar mantığıda yanlış olmaktadır. 
