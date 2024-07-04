import publicKey from '../keys/public.key?raw'
import privateKey from '../keys/private.key?raw'

function _base64StringToArrayBuffer(b64str) {
    const byteStr = atob(b64str)
    const bytes = new Uint8Array(byteStr.length)
    for (let i = 0; i < byteStr.length; i++) {
      bytes[i] = byteStr.charCodeAt(i)
    }
    return bytes.buffer
}

function _arrayBufferToBase64(arrayBuffer) {
    const byteArray = new Uint8Array(arrayBuffer);
    let byteString = '';
    for(let i=0; i < byteArray.byteLength; i++) {
        byteString += String.fromCharCode(byteArray[i]);
    }
    const b64 = window.btoa(byteString);
  
    return b64;
}

function _convertPemToArrayBuffer(pem) {
    const lines = pem.split('\n')
    let encoded = ''
    for(let i = 0;i < lines.length;i++){
      if (lines[i].trim().length > 0 &&
          lines[i].indexOf('-----BEGIN PRIVATE KEY-----') < 0 &&
          lines[i].indexOf('-----BEGIN PUBLIC KEY-----') < 0 &&
          lines[i].indexOf('-----END PRIVATE KEY-----') < 0 &&
          lines[i].indexOf('-----END PUBLIC KEY-----') < 0) {
        encoded += lines[i].trim()
      }
    }
    return _base64StringToArrayBuffer(encoded)
}

async function importKey(key, usage, format) {
    return await crypto.subtle.importKey(
        format,
        key,{
          name: "RSA-OAEP",
          extractable: true,
          hash: {
            name: "SHA-1"
          }
        },
        true,
        [usage]
    )
}

export async function encrypt(text) {
    let utf8Encode = new TextEncoder("utf-8");
    let publicK = _convertPemToArrayBuffer(publicKey)
    let data = utf8Encode.encode(text)
    let keypub = await importKey(publicK, "encrypt", "spki")
    const encryptedData = await crypto.subtle.encrypt(
        {
          name: "RSA-OAEP",
          extractable: true,
          hash: {
            name: "SHA-1"
          }
        },
        keypub, // from generateKey or importKey above
        data // ArrayBuffer of data you want to encrypt
      )
    return _arrayBufferToBase64(encryptedData)  
}

export async function decrypt(encryptedData) {
    let encryptedDataBuffer = _base64StringToArrayBuffer(encryptedData)
    let privateK = _convertPemToArrayBuffer(privateKey)
    let keypri = await importKey(privateK, "decrypt", "pkcs8")
    const decryptedBuffer = await crypto.subtle.decrypt(
        {
          name: 'RSA-OAEP',
        }, 
        keypri, 
        encryptedDataBuffer
    )
    const uint8Array = new Uint8Array(decryptedBuffer)
    const textDecoder = new TextDecoder();
    const decodedString = textDecoder.decode(uint8Array)
    return decodedString
}

