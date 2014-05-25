package gmusic.api.interfaces;

public interface IJsonDeserializer
{
    <T> T deserialize(String data, Class<T> clazz);
}