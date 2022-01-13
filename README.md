# redis & configurations & json

private JsonObject getIconImageInfo(JsonObject modelResult) {
		JsonObject response = new JsonObject();
		
		if(!modelResult.has("item") || !modelResult.get("item").isJsonArray()) {
			throw new ServiceException(ErrorCode.ERROR_THINQSS_INTERFACE);
		}
		
		JsonArray items = modelResult.get("item").getAsJsonArray();
		items.forEach(e->{
			JsonObject item = e.getAsJsonObject();
			
			// groupItem이 있는 경우
			if(item.has("groupItem") && !item.get("groupItem").isJsonNull()) {
				JsonArray groupItems = item.get("groupItem").getAsJsonArray();
				groupItems.forEach(g->{
					if(g.getAsJsonObject().has("subItem") && !g.getAsJsonObject().get("subItem").isJsonNull()) {		
						JsonArray subItems = g.getAsJsonObject().get("subItem").getAsJsonArray();
						subItems.forEach(s->{
							JsonObject icon = this.createIconJson(s.getAsJsonObject());
							if(icon != null) {
								response.add(s.getAsJsonObject().get("typeCode").getAsString(), icon);
							}
						});
					}
				});				
			// subitem이 있는 경우	
			} else if(item.has("subItem") && !item.get("subItem").isJsonNull()) {		
				JsonArray subItems = item.get("subItem").getAsJsonArray();
				subItems.forEach(s->{
					JsonObject icon = this.createIconJson(s.getAsJsonObject());
					if(icon != null) {
						response.add(s.getAsJsonObject().get("typeCode").getAsString(), icon);
					}
				});
				
			// subitem이 없으면 그대로 사용
			} else {
				JsonObject icon = this.createIconJson(e.getAsJsonObject());
				if(icon != null) {
					response.add(e.getAsJsonObject().get("typeCode").getAsString(), icon);
				}
			}
		});
		
		return response;
	}
	
	private JsonObject createIconJson(JsonObject item) {
		JsonObject icon = null;
		if(item != null &&
			item.has("typeCode") &&
			item.has("deviceType") &&
			item.has("imageIcon")) {
			icon = new JsonObject();
			icon.addProperty("typeCode", item.get("typeCode").getAsString());
			icon.addProperty("deviceType", item.get("deviceType").getAsString());
			icon.addProperty("fileName", item.get("imageIcon").getAsJsonObject().get("fileName").getAsString());
			icon.addProperty("fileExt", item.get("imageIcon").getAsJsonObject().get("fileExt").getAsString());
			icon.addProperty("fileUrl", item.get("imageIcon").getAsJsonObject().get("url").getAsString());
		} else {
			if(item != null) {
				LogUtils.error(item.toString());
			} else {
				LogUtils.error("Icon subItem is NULL");
			}
		}
		return icon;
	}
