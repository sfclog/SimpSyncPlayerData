#SimpSyncPlayerData | Thay thế MySQLPlayerDataBridge và HuskSync sử dụng MongoDB

Dùng MongoDB để đồng bộ với tốc độ cao giữa đa máy chủ 
Nhưng đôi lúc nó không đồng bộ nên không dùng nữa

Nguyên Lí Hoạt Động:
- Băm dữ liệu dạng itemstack về base64, nếu dữ liệu dạng chữ và xóa thì lưu trực tiếp
- Tạo OOP PlayerData và gán những dữ liệu đã thu thập vào
- Chuyển PlayerData về dạng json và up lên MongoDB
- Khi cần lấy dữ liệu thì getjson vs username đó rồi từ json chuyển về PlayerData, giải băm itemstack và thông số và set lại item và chỉ số cho người chơi

Có thể có bug ở phần bug và giải băm item đối với một số server dùng MMOitem thì dễ bị không đồng bộ vì data khi băm của item khá dài nên time đồng bộ ảnh hưởng, nếu có dùng thì dùng ở những server thường


