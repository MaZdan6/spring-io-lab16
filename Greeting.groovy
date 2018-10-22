@RestController
class Greeter {
  
  @GetMapping("/greet")
  def sayHelllo() {
    return "Hello"
  }
}
