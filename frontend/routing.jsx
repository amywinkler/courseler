
export let currentRoute = function() {
  let q = document.location.search;
  if (q.length > 0) q = q.substring(1); // cut off the '?'
  let dict = {};
  q.split('&').forEach((part) => {
    if (part.length > 0) {
      let key = part.split('=')[0];
      let value = decodeURIComponent(part.split('=')[1]);
      dict[key] = value;
    }
  })
  return dict;
}

export let navigateToRoute = function(route) {
  let url = '?' + Object.keys(route).map((k) => k + '=' + encodeURIComponent(route[k])).join('&');
  history.pushState({}, null, url);
  window.onpopstate();
  window.scrollTo(0, 0)
}

