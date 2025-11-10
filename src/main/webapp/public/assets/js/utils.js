function decodeHtml(html) {
  const parser = new DOMParser();
  const decodedString = parser.parseFromString(html, "text/html");
  return decodedString.body.textContent;
}

export { decodeHtml };